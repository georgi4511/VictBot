package com.github.georgi4511.victbot.listener;

import com.github.georgi4511.victbot.exception.CommandUnderCooldownException;
import com.github.georgi4511.victbot.model.CommandCooldownRecord;
import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.service.BookmarkService;
import com.github.georgi4511.victbot.service.VictGuildService;
import com.github.georgi4511.victbot.service.VictUserService;
import com.github.georgi4511.victbot.util.Util;
import java.time.Instant;
import java.util.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!dev")
public class DiscordEventListener extends ListenerAdapter {

  private static final Logger log = LoggerFactory.getLogger(DiscordEventListener.class);
  private final Map<String, VictCommand> commandMap;
  private final VictGuildService victGuildService;
  private final VictUserService victUserService;
  private final BookmarkService bookmarkService;

  private final Map<User, ArrayList<CommandCooldownRecord>> commandCooldownMap;

  @Value("${vict.twitter.fix:true}")
  boolean fixTwitterFlag;

  @Value("${vict.discord.guilds}")
  private List<String> guilds;

  DiscordEventListener(
      List<VictCommand> commandList,
      VictUserService victUserService,
      VictGuildService victGuildService,
      BookmarkService bookmarkService) {
    this.commandMap = new HashMap<>();
    commandList.forEach(command -> commandMap.put(command.getName(), command));

    this.victUserService = victUserService;
    this.victGuildService = victGuildService;
    this.bookmarkService = bookmarkService;

    this.commandCooldownMap = new HashMap<>();
  }

  @Override
  public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
    String commandName = event.getName();
    VictCommand command = commandMap.get(commandName);

    User eventUser = event.getUser();
    Guild eventGuild = event.getGuild();

    validateExistingOrCreateEntities(eventUser, eventGuild);

    try {
      handleSlashCommandExecution(event, eventUser, command);
    } catch (UnsupportedOperationException e) {
      event.reply("Unknown command").setEphemeral(true).queue();
    } catch (CommandUnderCooldownException e) {
      event
          .reply(String.format("%s, %s", event.getUser().getAsMention(), e.getMessage()))
          .setEphemeral(true)
          .queue();
    }
  }

  private void handleSlashCommandExecution(
      @NotNull SlashCommandInteractionEvent event, User eventUser, VictCommand command) {
    if (command == null) {
      throw new UnsupportedOperationException();
    }

    ArrayList<CommandCooldownRecord> cooldownRecords = commandCooldownMap.get(eventUser);

    if (cooldownRecords == null) {
      cooldownRecords = new ArrayList<>();
    }

    String commandName = command.getName();
    Long commandCooldown = command.getCooldown();

    validateCommandExecution(cooldownRecords, commandName, commandCooldown);

    command.callback(event);

    if (commandCooldown != 0) {
      CommandCooldownRecord cooldownRecord =
          new CommandCooldownRecord(Instant.now().plusSeconds(commandCooldown), commandName);
      cooldownRecords.add(cooldownRecord);
      commandCooldownMap.put(eventUser, cooldownRecords);
    }
  }

  private void validateCommandExecution(
      ArrayList<CommandCooldownRecord> cooldownRecords, String commandName, Long commandCooldown) {
    if (cooldownRecords.isEmpty()) return;
    if (commandCooldown == 0) return;
    Optional<CommandCooldownRecord> cooldownRecordOptional =
        cooldownRecords.stream().filter(li -> li.name().equals(commandName)).findFirst();

    if (cooldownRecordOptional.isEmpty()) return;

    CommandCooldownRecord cooldownRecord = cooldownRecordOptional.get();
    Instant created = cooldownRecord.created();

    Instant now = Instant.now();
    if (created.getEpochSecond() > now.getEpochSecond()) {
      throw new CommandUnderCooldownException(
          String.format(
              "the %s command is under cooldown for another %s seconds.",
              commandName, now.until(created).getSeconds()));
    }
    cooldownRecords.remove(cooldownRecord);
  }

  private void validateExistingOrCreateEntities(User eventUser, Guild eventGuild) {
    if (!victUserService.existsById(eventUser.getId())) {
      victUserService.save(new VictUser(eventUser.getId()));
    }
    if (eventGuild != null && !victGuildService.existsById(eventGuild.getId())) {
      victGuildService.save(new VictGuild(eventGuild.getId()));
    }
  }

  @Override
  public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
    String customId = event.getComponentId();

    // Assuming custom IDs are in the format "commandName_menuIdentifier"
    VictCommand command = commandMap.get(customId.split("_", 2)[0]);
    if (command != null) {
      command.handleSelectInteraction(event);
    } else {
      event.reply("Unknown select interaction").setEphemeral(true).queue();
    }
  }

  @Override
  public void onMessageReceived(@NotNull MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;

    String content = event.getMessage().getContentRaw();
    if (fixTwitterFlag) {
      Util.fixTwitter(event, content);
      return;
    }

    if (content.startsWith("!")) {
      String alias = content.substring(1);
      bookmarkService
          .getBookmarkByAlias(alias)
          .ifPresent(
              bookmark ->
                  event.getChannel().asTextChannel().sendMessage(bookmark.getResponse()).queue());
    }
  }

  @Override
  public void onReady(@NotNull ReadyEvent event) {
    JDA jda = event.getJDA();

    log.info("{} logged in.", jda.getSelfUser().getEffectiveName());

    Collection<VictCommand> commands = commandMap.values();

    String commandLog = commands.stream().map(VictCommand::getName).toList().toString();
    log.info(commandLog);

    if (guilds.isEmpty()) {
      log.info("Setting commands for production");
      jda.updateCommands()
          .addCommands(
              commands.stream()
                  .filter(victCommand -> !victCommand.getDevCommand())
                  .map(VictCommand::getData)
                  .toList())
          .queue();
    }

    guilds.forEach(
        guildS -> {
          Guild guild = jda.getGuildById(guildS);
          if (guild != null) {
            log.info("Setting commands for development in guild: {}", guild.getName());
            guild
                .updateCommands()
                .addCommands(commands.stream().map(VictCommand::getData).toList())
                .queue();
          }
        });

    log.info("{} commands set", commandMap.size());
  }
}
