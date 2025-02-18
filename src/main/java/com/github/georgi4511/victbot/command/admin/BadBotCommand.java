/* (C)2025 */
package com.github.georgi4511.victbot.command.admin;

import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.entity.VictCommand;
import com.github.georgi4511.victbot.service.ImpressionsService;
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
public class BadBotCommand extends VictCommand {
  private static final Logger log = LoggerFactory.getLogger(BadBotCommand.class);
  private final ImpressionsService impressionsService;
  private SlashCommandData data;
  private String name;
  private String description;

  public BadBotCommand(ImpressionsService impressionsService) {
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
}
