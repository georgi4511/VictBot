package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.service.ReminderService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

  private final ReminderService reminderService;

  @GetMapping
  public List<Reminder> getAllReminders() {
    return reminderService.findAll();
  }

  @PostMapping
  public Reminder saveReminder(@RequestBody Reminder reminder) {
    return reminderService.create(reminder);
  }

  @DeleteMapping
  public ResponseEntity<Object> removeReminderById(Long id) {
    reminderService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/guild/{victGuildId}")
  public List<Reminder> getReminder(@PathVariable String victGuildId) {
    return reminderService.findByVictGuildId(victGuildId);
  }

  @GetMapping("/{id}")
  public Optional<Reminder> getReminderById(@PathVariable Long id) {
    return reminderService.findById(id);
  }
}
