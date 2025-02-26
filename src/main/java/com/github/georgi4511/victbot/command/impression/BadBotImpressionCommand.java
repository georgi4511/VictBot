package com.github.georgi4511.victbot.command.impression;

import com.github.georgi4511.victbot.model.Impressions;
import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.ImpressionsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class BadBotImpressionCommand implements VictCommand {
  private static final Logger log = LoggerFactory.getLogger(BadBotImpressionCommand.class);
  private final ImpressionsService impressionsService;
  private SlashCommandData data = Commands.slash("bad-bot", "When bot is bad");

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
