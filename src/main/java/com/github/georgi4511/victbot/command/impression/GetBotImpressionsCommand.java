package com.github.georgi4511.victbot.command.impression;

import com.github.georgi4511.victbot.model.Impressions;
import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.ImpressionsService;
import com.github.georgi4511.victbot.service.VictGuildService;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class GetBotImpressionsCommand extends VictCommand {
  private static final Logger log = LoggerFactory.getLogger(GetBotImpressionsCommand.class);
  @NonNull private final ImpressionsService impressionsService;
  private final VictGuildService victGuildService;
  private SlashCommandData data;
  private String name;
  private String description;

  public GetBotImpressionsCommand(
      @NotNull ImpressionsService impressionsService, VictGuildService victGuildService) {
    this.name = "get-bot-impressions";
    this.description = "Get amount of bot is good/bad sent in server/globally";
    this.data =
        Commands.slash(this.name, this.description)
            .addOption(
                OptionType.BOOLEAN, "global", "do the search globally or only for this server");
    this.impressionsService = impressionsService;
    this.victGuildService = victGuildService;
  }

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
    Impressions impressions = impressionsService.getImpressionsOrCreateByDiscordId(guild.getId());

    event
        .reply(
            String.format(
                "I have received %d good bots and %d bad bots in this server",
                impressions.getGoodBotCount(), impressions.getBadBotCount()))
        .queue();
  }

  private void returnGlobalImpressions(SlashCommandInteractionEvent event) {
    List<Impressions> allImpressions = impressionsService.getAllImpressions();

    Integer badBotSum =
        allImpressions.stream().map(Impressions::getBadBotCount).reduce(0, Integer::sum);
    Integer goodBotSum =
        allImpressions.stream().map(Impressions::getGoodBotCount).reduce(0, Integer::sum);

    event
        .reply(
            String.format(
                "I have received %d good bots and %d bad bots globally", goodBotSum, badBotSum))
        .queue();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    GetBotImpressionsCommand that = (GetBotImpressionsCommand) o;
    return Objects.equals(impressionsService, that.impressionsService)
        && Objects.equals(victGuildService, that.victGuildService)
        && Objects.equals(data, that.data)
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(), impressionsService, victGuildService, data, name, description);
  }
}
