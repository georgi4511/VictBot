package com.github.georgi4511.victbot.command.reminder;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.model.VictCommand;
import com.github.georgi4511.victbot.service.ReminderService;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class ListRemindersCommand implements VictCommand {
  public static final String SHOW_MESSAGE = "show-message";
  private static final Logger log = LoggerFactory.getLogger(ListRemindersCommand.class);
  private final ReminderService reminderService;
  private SlashCommandData data =
      Commands.slash("list-reminders", "Lists all your reminders for whatever whenever")
          .addOption(
              OptionType.BOOLEAN, SHOW_MESSAGE, "to show the message or keep it hidden", true);

  @Override
  public void callback(SlashCommandInteractionEvent event) {
    try {

      List<Reminder> reminders = reminderService.findRemindersByUserId(event.getUser().getId());
      if (reminders.isEmpty()) {
        event.reply("There are no currently existing reminders, how about you add one?").queue();
        return;
      }

      boolean showMessages = Objects.requireNonNull(event.getOption(SHOW_MESSAGE)).getAsBoolean();

      String message =
          reminders.stream()
              .map(
                  r -> {
                    if (showMessages) {
                      return String.format(
                          "Created at: %s, Message: %s, Target time:%s",
                          r.getCreatedTime()
                              .atZone(ZoneId.systemDefault())
                              .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                          r.getMessage(),
                          r.getTargetTime()
                              .atZone(ZoneId.systemDefault())
                              .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
                    }
                    return String.format(
                        "Created at: %s, Target time:%s",
                        r.getCreatedTime()
                            .atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                        r.getTargetTime()
                            .atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
                  })
              .reduce("Currently existing reminders:", (agg, curr) -> agg + "\n" + curr);

      event.reply(message).queue();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      if (!event.isAcknowledged()) {
        event.reply("Failed to execute command").queue();
      }
    }
  }
}
