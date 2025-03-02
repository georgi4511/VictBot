package com.github.georgi4511.victbot.command.impression;

import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.VictGuildService;
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
@RequiredArgsConstructor
@Component
public class GoodBotImpressionCommand implements VictCommand {
  private static final Logger log = LoggerFactory.getLogger(GoodBotImpressionCommand.class);
  private final VictGuildService victGuildService;
  private SlashCommandData data = Commands.slash("good-bot", "When bot is good");

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {
      Guild guild = event.getGuild();
      if (guild == null) {
        throw new UnsupportedOperationException();
      }

      Long impressions = victGuildService.incrementGoodBotImpressions(guild.getId());

      event
          .reply(
              String.format(
                  "I have received %d good bot impressions. Thank you very much.", impressions))
          .queue();

    } catch (Exception e) {
      log.error(e.getMessage());
      if (!event.isAcknowledged()) {
        event.getHook().editOriginal("Command failed to execute").queue();
      }
    }
  }
}
