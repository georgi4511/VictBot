package com.github.georgi4511.victbot.command.utility;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class GetTimeCommand extends AbstractVictCommand {
  private SlashCommandData data = Commands.slash("get-time", "gets the time, cmon");
  private Boolean devCommand = true;

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    slashCommandInteractionEvent
        .reply(
            Instant.now()
                .atZone(ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
        .queue();
  }

}
