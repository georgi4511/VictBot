package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.repository.ReminderRepository;
import com.github.georgi4511.victbot.repository.VictGuildRepository;
import com.github.georgi4511.victbot.repository.VictUserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReminderService {
  final ReminderRepository reminderRepository;
  final VictGuildRepository victGuildRepository;
  final VictUserRepository victUserRepository;

  public List<Reminder> getAllReminderEntry() {
    return reminderRepository.findAll();
  }

  public List<Reminder> getRemindersByVictUser(VictUser victUser) {
    return reminderRepository.findByVictUser(victUser);
  }

  public List<Reminder> getRemindersByVictUserAndVictGuild(VictGuild victGuild, VictUser victUser) {
    return reminderRepository.findByVictGuildAndVictUser(victGuild, victUser);
  }

  public List<Reminder> getRemindersByVictGuild(VictGuild victGuild) {
    return reminderRepository.findByVictGuild(victGuild);
  }

  public List<Reminder> getRemindersByDiscordId(String id) {
    VictUser victUser =
        victUserRepository.findByDiscordId(id).orElseThrow(NullPointerException::new);
    return reminderRepository.findByVictUser(victUser);
  }

  public void removeReminders(List<Reminder> reminders) {
    reminderRepository.deleteAll(reminders);
  }

  public void removeReminder(Reminder reminders) {
    reminderRepository.delete(reminders);
  }

  public Reminder saveReminder(Reminder reminder) {
    return reminderRepository.save(reminder);
  }

  public void saveReminder(
      String message,
      Boolean personal,
      Instant targetTime,
      String channelSentFrom,
      String guildId,
      String userId) {

    Instant now = Instant.now();
    Reminder reminder =
        Reminder.builder()
            .id(null)
            .createdTime(now)
            .victUser(null)
            .victGuild(null)
            .message(message)
            .personal(personal)
            .targetTime(targetTime)
            .channelSentFrom(channelSentFrom)
            .build();

    if (guildId != null) {
      VictGuild victGuild =
          victGuildRepository
              .findByDiscordId(guildId)
              .orElseThrow(() -> new EntityNotFoundException("Vict Guild not found"));
      reminder.setVictGuild(victGuild);
    }

    VictUser victUser =
        victUserRepository
            .findByDiscordId(userId)
            .orElseThrow(() -> new EntityNotFoundException("Vict User not found"));
    reminder.setVictUser(victUser);

    reminderRepository.save(reminder);
  }

  public void removeById(Long id) {
    reminderRepository.deleteById(id);
  }

  public Optional<Reminder> getReminderById(Long id) {
    return reminderRepository.findById(id);
  }
}
