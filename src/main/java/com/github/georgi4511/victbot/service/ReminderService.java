package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.repository.ReminderRepository;
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
  final VictGuildService victGuildService;
  final VictUserService victUserService;

  public List<Reminder> findAllReminderEntry() {
    return reminderRepository.findAll();
  }

  public List<Reminder> findRemindersByVictUserId(String victUserId) {
    return reminderRepository.findByVictUserId(victUserId);
  }

  public List<Reminder> findRemindersByVictUserAndVictGuild(String victGuildId, String victUserId) {
    return reminderRepository.findByVictGuildIdAndVictUserId(victGuildId, victUserId);
  }

  public List<Reminder> findRemindersByVictGuildId(String victGuildId) {
    return reminderRepository.findByVictGuildId(victGuildId);
  }

  public List<Reminder> findRemindersByUserId(String victUserId) {
    return reminderRepository.findByVictUserId(victUserId);
  }

  public void deleteReminders(List<Reminder> reminders) {
    reminderRepository.deleteAll(reminders);
  }

  public void deleteReminder(Reminder reminders) {
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
          victGuildService
              .findById(guildId)
              .orElseThrow(() -> new EntityNotFoundException("Vict Guild not found"));
      reminder.setVictGuild(victGuild);
    }

    VictUser victUser =
        victUserService
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Vict User not found"));
    reminder.setVictUser(victUser);

    reminderRepository.save(reminder);
  }

  public void deleteById(Long id) {
    reminderRepository.deleteById(id);
  }

  public Optional<Reminder> findReminderById(Long id) {
    return reminderRepository.findById(id);
  }
}
