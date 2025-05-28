package com.github.georgi4511.victbot.command.reminder;

import com.github.georgi4511.victbot.model.AbstractVictCommand;
import com.github.georgi4511.victbot.service.ReminderService;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@Component
public class AddReminderCommand extends AbstractVictCommand {
  public static final String MESSAGE = "message";
  public static final String PERSONAL = "personal";
  public static final String TARGET_TIME = "target-time";
  public static final String TIME_UNIT = "time-unit";
  public static final String MINUTES = "minutes";
  public static final String HOURS = "hours";
  public static final String DAYS = "days";
  private static final Logger log = LoggerFactory.getLogger(AddReminderCommand.class);
  private static final OptionData MESSAGE_OPTION_DATA =
      new OptionData(OptionType.STRING, MESSAGE, "message to send when reminder pops", true);

  private static final OptionData PERSONAL_OPTION_DATA =
      new OptionData(
          OptionType.BOOLEAN, PERSONAL, "send reminder in DM or in current channel", true);

  private static final OptionData TIME_UNIT_OPTION_DATA =
      new OptionData(OptionType.STRING, TIME_UNIT, "what time unit do you want to use", true)
          .addChoices(
              new Command.Choice(MINUTES, MINUTES),
              new Command.Choice(HOURS, HOURS),
              new Command.Choice(DAYS, DAYS));

  private static final OptionData TARGET_TIME_OPTION_DATA =
      new OptionData(
          OptionType.INTEGER, TARGET_TIME, "in how many units of time should this trigger", true);

  private final SlashCommandData data =
      Commands.slash("add-reminder", "Adds a reminder for whatever whenever")
          .addOptions(
              MESSAGE_OPTION_DATA,
              PERSONAL_OPTION_DATA,
              TARGET_TIME_OPTION_DATA,
              TIME_UNIT_OPTION_DATA);

  private final ReminderService reminderService;

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    String message = "";
    boolean personal = false;
    int time = 1;
    String chronoUnitS = MINUTES;
    List<OptionMapping> options = event.getOptions();

    for (OptionMapping option : options) {
      switch (option.getName()) {
        case MESSAGE -> message = option.getAsString();
        case PERSONAL -> personal = option.getAsBoolean();
        case TARGET_TIME -> time = option.getAsInt();
        case TIME_UNIT -> chronoUnitS = option.getAsString();
        default -> log.info("Unexpected option received, {}", option.getName());
      }
    }

    String channelId = event.getChannelId();

    Guild guild = event.getGuild();
    String guildId = guild == null ? null : guild.getId();
    String userId = event.getUser().getId();

    Instant targetTime = Instant.now().plus(time, ChronoUnit.valueOf(chronoUnitS.toUpperCase()));
    reminderService.create(message, personal, targetTime, channelId, guildId, userId);

    event
        .reply(
            String.format(
                "Set reminder for: %s",
                targetTime
                    .atZone(ZoneId.systemDefault())
                    .format(
                        DateTimeFormatter.ofLocalizedDateTime(
                            FormatStyle.FULL, FormatStyle.MEDIUM))))
        .queue();
  }
}
