package com.github.georgi4511.victbot.command.utility;

import com.github.georgi4511.victbot.model.AbstractVictCommand;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class GetTimeCommand extends AbstractVictCommand {
  private final OptionData locationData =
      new OptionData(
              OptionType.STRING,
              "location",
              "What location to get the current time of, defaults to UTC",
              false,
              false)
          .addChoices(
              ZoneId.SHORT_IDS.values().stream()
                  .map(
                      zoneId ->
                          new Command.Choice(
                              zoneId, zoneId))
                  .toList()
                  .subList(0, 23))
          .addChoice("UTC", "UTC");

  private SlashCommandData data =
      Commands.slash("get-time", "gets the time, cmon").addOptions(locationData);
  private Boolean devCommand = false;

  @Override
  public void callback(SlashCommandInteractionEvent event) {

    OptionMapping locationOption = event.getOption("location");

    String location = locationOption == null ? "UTC" : locationOption.getAsString();

    event
        .reply(
            Instant.now()
                .atZone(ZoneId.of(location))
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
        .queue();
  }
}
