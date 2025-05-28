package com.github.georgi4511.victbot.procedures;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.service.ReminderService;
import java.time.Instant;
import java.util.List;
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
  final JDA jda;

  @Scheduled(cron = "${vict.procedure.reminder.cron}")
  @Async
  @Override
  public void handleReminders() {
    List<Reminder> reminders = reminderService.findAll();
    Instant now = Instant.now();
    List<Reminder> triggeredReminders =
        reminders.stream().filter(reminder -> now.isAfter(reminder.targetTime())).toList();

    if (triggeredReminders.isEmpty()) {
      return;
    }

    triggeredReminders.forEach(this::sendReminder);
    reminderService.delete(triggeredReminders);
  }

  @Override
  public void sendReminder(Reminder reminder) {

    String victUserId = reminder.victUser().id();
    User user = jda.getUserById(victUserId);

    if (user == null) {
      log.info("Failed to send reminder, {} user is not a real user", victUserId);
      return;
    }

    String message =
        String.format("%s, you have a reminder:%n%s", user.getAsMention(), reminder.message());

    if (reminder.personal()) {
      user.openPrivateChannel().flatMap(channel -> channel.sendMessage(message)).queue();
      return;
    }

    String channelId = reminder.channelSentFrom();
    TextChannel channel = jda.getChannelById(TextChannel.class, channelId);

    if (channel == null) {
      return;
    }

    channel.sendMessage(message).queue();
  }
}
