package com.github.georgi4511.victbot.procedures;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.service.ReminderService;
import com.github.georgi4511.victbot.service.VictUserService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReminderProcedure {

  private static final Logger log = LoggerFactory.getLogger(ReminderProcedure.class);
  final ReminderService reminderService;
  final VictUserService victUserService;
  final JDA jda;

  @Scheduled(cron = "${vict.procedure.reminder.cron}")
  @Async
  public void handleReminders() {
    List<Reminder> allReminderEntry = reminderService.getAllReminderEntry();
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
      reminderService.removeReminders(reminders);

    } catch (NullPointerException e) {
      log.error("Failed to complete reminder procedure, {}", e.getMessage(), e);
    }
  }

  private void sendReminder(Reminder reminder) {

    Long victUserId = reminder.getVictUser().getId();
    VictUser victUser =
        victUserService.getByVictUserId(victUserId).orElseThrow(EntityNotFoundException::new);
    User user = Objects.requireNonNull(jda.getUserById(victUser.getDiscordId()));

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
