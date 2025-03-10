package com.github.georgi4511.victbot.procedures;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.service.ReminderService;
import com.github.georgi4511.victbot.service.VictUserService;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Profile("!dev")
public class ReminderProcedureImpl implements ReminderProcedure {

  private static final Logger log = LoggerFactory.getLogger(ReminderProcedureImpl.class);
  final ReminderService reminderService;
  final VictUserService victUserService;
  final JDA jda;

  @Scheduled(cron = "${vict.procedure.reminder.cron}")
  @Async
  @Override
  public void handleReminders() {
    List<Reminder> allReminderEntry = reminderService.findAllReminderEntry();
    Instant now = Instant.now();
    List<Reminder> reminders =
        allReminderEntry.stream()
            .filter(reminder -> now.isAfter(reminder.getTargetTime()))
            .toList();

    if (reminders.isEmpty()) {
      return;
    }

    try {
      reminders.forEach(this::sendReminder);
      reminderService.deleteReminders(reminders);

    } catch (NullPointerException e) {
      log.error("Failed to complete reminder procedure, {}", e.getMessage(), e);
    }
  }

  @Override
  public void sendReminder(Reminder reminder) {

    String victUserId = reminder.getVictUser().getId();
    User user = Objects.requireNonNull(jda.getUserById(victUserId));

    String message =
        String.format("%s, you have a reminder:%n%s", user.getAsMention(), reminder.getMessage());

    if (reminder.getPersonal()) {
      user.openPrivateChannel().flatMap(channel -> channel.sendMessage(message)).queue();
    } else {
      String channelSentFrom = reminder.getChannelSentFrom();
      Objects.requireNonNull(jda.getChannelById(TextChannel.class, channelSentFrom))
          .sendMessage(message)
          .queue();
    }
  }
}
