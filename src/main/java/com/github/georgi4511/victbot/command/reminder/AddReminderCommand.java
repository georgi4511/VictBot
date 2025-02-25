package com.github.georgi4511.victbot.command.reminder;

import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.ReminderService;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter
@Component
public class AddReminderCommand extends VictCommand {
  public static final String MESSAGE = "message";
  public static final String PERSONAL = "personal";
  public static final String TARGET_TIME = "target-time";
  public static final String TIME_UNIT = "time-unit";
  public static final String MINUTES = "minutes";
  public static final String HOURS = "hours";
  public static final String DAYS = "days";
  public static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM);
  private static final Logger log = LoggerFactory.getLogger(AddReminderCommand.class);
  private final ReminderService reminderService;

  private SlashCommandData data;
  private String name;
  private String description;

  public AddReminderCommand(ReminderService reminderService) {

    OptionData message =
        new OptionData(OptionType.STRING, MESSAGE, "message to send when reminder pops", true);
    OptionData personal =
        new OptionData(
            OptionType.BOOLEAN, PERSONAL, "send reminder in DM or in current channel", true);
    OptionData timeUnit =
        new OptionData(OptionType.STRING, TIME_UNIT, "what time unit do you want to use", true)
            .addChoices(
                new Command.Choice(MINUTES, MINUTES),
                new Command.Choice(HOURS, HOURS),
                new Command.Choice(DAYS, DAYS));
    OptionData targetTime =
        new OptionData(
            OptionType.INTEGER, TARGET_TIME, "in how many units of time should this trigger", true);

    this.name = "add-reminder";
    this.description = "Adds a reminder for whatever whenever";
    this.data =
        Commands.slash(this.name, this.description)
            .addOptions(message, personal, targetTime, timeUnit);
    this.reminderService = reminderService;
  }

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {
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
      reminderService.saveReminder(message, personal, targetTime, channelId, guildId, userId);

      event
          .reply(String.format("Set reminder for: %s", DATE_TIME_FORMATTER.format(targetTime)))
          .queue();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      if (!event.isAcknowledged()) {
        event.reply("Failed to execute command").queue();
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    AddReminderCommand that = (AddReminderCommand) o;
    return Objects.equals(data, that.data)
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(reminderService, that.reminderService);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), data, name, description, reminderService);
  }
}
