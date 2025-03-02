package com.github.georgi4511.victbot.command.impression;

import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.service.VictGuildService;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class GetBotImpressionsCommand implements VictCommand {
  private static final Logger log = LoggerFactory.getLogger(GetBotImpressionsCommand.class);
  private final VictGuildService victGuildService;
  private SlashCommandData data = Commands
      .slash("get-bot-impressions", "Get amount of bot is good/bad sent in server/globally")
      .addOption(
          OptionType.BOOLEAN, "global", "do the search globally or only for this server");

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {

      boolean global = true;
      OptionMapping globalOption = event.getOption("global");
      if (null != globalOption) {
        global = globalOption.getAsBoolean();
      }

      if (global) {
        returnGlobalImpressions(event);
        return;
      }
      returnGuildImpressions(event);

    } catch (Exception e) {
      log.error(e.getMessage());
      event.getHook().sendMessage("Command failed to execute").setEphemeral(true).queue();
    }
  }

  private void returnGuildImpressions(SlashCommandInteractionEvent event) {
    Guild guild = event.getGuild();
    if (guild == null) {
      throw new UnsupportedOperationException("Global but not in guild");
    }

    VictGuild victGuild = victGuildService.findByIdOrCreate(guild.getId());

    event
        .reply(
            String.format(
                "I have received %d good bots and %d bad bots in this server",
                victGuild.getGoodBotImpressions(), victGuild.getBadBotImpressions()))
        .queue();
  }

  private void returnGlobalImpressions(SlashCommandInteractionEvent event) {
    List<VictGuild> allGuilds = victGuildService.findAll();

    Long badBotSum = allGuilds.stream().map(VictGuild::getBadBotImpressions).reduce(0L, Long::sum);
    Long goodBotSum = allGuilds.stream().map(VictGuild::getGoodBotImpressions).reduce(0L, Long::sum);
    event.reply(
        String.format(
            "I have received %d good bots and %d bad bots globally", goodBotSum, badBotSum))
        .queue();
  }
}
