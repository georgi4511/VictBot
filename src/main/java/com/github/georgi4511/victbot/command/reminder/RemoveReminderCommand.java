package com.github.georgi4511.victbot.command.reminder;

import com.github.georgi4511.victbot.command.AbstractVictCommand;
import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.service.ReminderService;
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
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@RequiredArgsConstructor
public class RemoveReminderCommand extends AbstractVictCommand {
  public static final String MESSAGE = "message";
  private final SlashCommandData data =
      Commands.slash(
              "remove-reminder", "Removes all reminders by user or single by using the set message")
          .addOption(OptionType.STRING, MESSAGE, "Message of the reminder", false);
  private final ReminderService reminderService;

  @Override
  protected void handleSlashCommandInteraction(SlashCommandInteractionEvent slashCommandInteractionEvent) {
    OptionMapping messageOption = slashCommandInteractionEvent.getOption(MESSAGE);
    String victUserId = Objects.requireNonNull(slashCommandInteractionEvent.getUser()).getId();

    List<Reminder> reminders;
    if (messageOption != null) {
      String messageS = messageOption.getAsString();
      reminders = reminderService.findByVictUserIdAndMessage(victUserId, messageS);
    } else {
      reminders = reminderService.findByVictUserId(victUserId);
    }

    if (reminders.isEmpty()) {
      slashCommandInteractionEvent.reply("No reminders found, aborting").queue();
      return;
    }

    reminderService.deleteAll(reminders);

    slashCommandInteractionEvent.reply("Reminders deleted").queue();
  }
}
