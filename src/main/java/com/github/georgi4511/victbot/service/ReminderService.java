package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.repository.ReminderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReminderService {

  private final ReminderRepository reminderRepository;
  private final VictGuildService victGuildService;
  private final VictUserService victUserService;

  public List<Reminder> findAll() {
    return reminderRepository.findAll();
  }

  public List<Reminder> findByVictUserId(String victUserId) {
    return reminderRepository.findByVictUserId(victUserId);
  }

  public List<Reminder> findByVictGuildId(String victGuildId) {
    return reminderRepository.findByVictGuildId(victGuildId);
  }

  public void deleteAll(List<Reminder> reminders) {
    reminderRepository.deleteAll(reminders);
  }

  public Reminder create(Reminder reminder) {
    return reminderRepository.save(reminder);
  }

  public void create(
      String message,
      Boolean personal,
      Instant targetTime,
      String channelSentFrom,
      String guildId,
      String userId) {

    Instant now = Instant.now();

    VictGuild victGuild =
        guildId == null
            ? null
            : victGuildService
                .findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Vict Guild not found"));

    VictUser victUser =
        victUserService
            .findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Vict User not found"));

    Reminder reminder =
        new Reminder(
            null, now, message, targetTime, victUser, victGuild, channelSentFrom, personal);

    reminderRepository.save(reminder);
  }

  public void deleteById(Long id) {
    reminderRepository.deleteById(id);
  }

  public Optional<Reminder> findById(Long id) {
    return reminderRepository.findById(id);
  }

  public List<Reminder> findByVictUserIdAndMessage(String victUserId, String message) {
    return reminderRepository.findByVictUserIdAndMessage(victUserId, message);
  }
}
