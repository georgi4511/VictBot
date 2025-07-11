package com.github.georgi4511.victbot.command.reminder;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.service.ReminderService;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class ListRemindersCommand extends AbstractVictCommand {
  public static final String SHOW_MESSAGE = "show-message";
  public static final String SHOW_GUILDS = "show-guilds";
  public static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.of("UTC"));
  private static final Logger log = LoggerFactory.getLogger(ListRemindersCommand.class);
  private final ReminderService reminderService;
  private SlashCommandData data =
      Commands.slash("list-reminders", "Lists all your reminders for whatever whenever")
          .addOption(
              OptionType.BOOLEAN, SHOW_MESSAGE, "to show the message or keep it hidden", true)
          .addOption(
              OptionType.BOOLEAN,
              SHOW_GUILDS,
              "to show the guilds bookmarks instead of you own",
              false);

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    List<Reminder> reminders = getReminders(slashCommandInteractionEvent);
    if (reminders.isEmpty()) {
      slashCommandInteractionEvent.reply("There are no currently existing reminders, how about you add one?").queue();
      return;
    }

    boolean showMessages = Objects.requireNonNull(slashCommandInteractionEvent.getOption(SHOW_MESSAGE)).getAsBoolean();

    String message =
        reminders.stream()
            .map(
                reminder -> {
                  String line =
                      String.format(
                          "Created at: %s, Target time:%s",
                          DATE_TIME_FORMATTER.format(reminder.getCreatedTime()),
                          DATE_TIME_FORMATTER.format(reminder.getTargetTime()));

                  if (showMessages) {
                    return line.concat(String.format(", Message:%n %s", reminder.getMessage()));
                  }

                  return line.concat(String.format("%n"));
                })
            .reduce(String.format("Currently existing reminders:%n"), String::concat);

    slashCommandInteractionEvent.reply(message).queue();
  }

  private List<Reminder> getReminders(SlashCommandInteractionEvent event) {
    OptionMapping reminderGuildOption = event.getOption(SHOW_GUILDS);

    List<Reminder> reminders;

    if (event.isFromGuild() && reminderGuildOption != null && reminderGuildOption.getAsBoolean()) {
      reminders =
          reminderService.findByVictGuildId(Objects.requireNonNull(event.getGuild()).getId());
    } else {
      reminders = reminderService.findByVictUserId(event.getUser().getId());
    }
    return reminders;
  }
}
