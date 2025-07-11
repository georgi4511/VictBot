package com.github.georgi4511.victbot.command.impression;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import com.github.georgi4511.victbot.service.VictGuildService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@Component
public class GoodBotImpressionCommand extends AbstractVictCommand {
  private static final Logger log = LoggerFactory.getLogger(GoodBotImpressionCommand.class);
  private final VictGuildService victGuildService;
  private SlashCommandData data = Commands.slash("good-bot", "When bot is good");

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    Guild guild = slashCommandInteractionEvent.getGuild();
    if (guild == null) {
      throw new UnsupportedOperationException();
    }

    Long impressions = victGuildService.incrementGoodBotImpressions(guild.getId());

    slashCommandInteractionEvent
        .reply(
            String.format(
                "I have received %d good bot impressions. Thank you very much.", impressions))
        .queue();
  }
}
