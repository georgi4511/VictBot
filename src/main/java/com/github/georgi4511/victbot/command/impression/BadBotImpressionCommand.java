package com.github.georgi4511.victbot.command.impression;

import com.github.georgi4511.victbot.model.Impressions;
import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.ImpressionsService;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BadBotImpressionCommand extends VictCommand {
  private static final Logger log = LoggerFactory.getLogger(BadBotImpressionCommand.class);
  private final ImpressionsService impressionsService;
  private SlashCommandData data;
  private String name;
  private String description;

  public BadBotImpressionCommand(ImpressionsService impressionsService) {
    this.name = "bad-bot";
    this.description = "When bot is bad";
    this.data = Commands.slash(this.name, this.description);
    this.impressionsService = impressionsService;
  }

  @Override
  public void callback(SlashCommandInteractionEvent event) {

    try {
      Guild guild = event.getGuild();
      if (guild == null) {
        throw new UnsupportedOperationException();
      }

      Impressions impressions =
          impressionsService.incrementImpressionsByDiscordId(guild.getId(), false);

      event
          .reply(
              String.format(
                  "I have received %d bad bot impressions. Frick you.",
                  impressions.getBadBotCount()))
          .queue();

    } catch (Exception e) {
      log.error(e.getMessage());
      if (!event.isAcknowledged()) {
        event.reply("Command failed to execute").setEphemeral(true).queue();
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    BadBotImpressionCommand that = (BadBotImpressionCommand) o;
    return Objects.equals(impressionsService, that.impressionsService)
        && Objects.equals(data, that.data)
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), impressionsService, data, name, description);
  }
}
