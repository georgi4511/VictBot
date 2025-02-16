package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.entity.Reminder;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.service.ReminderEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderEntryService reminderEntryService;

    @GetMapping
    public List<Reminder> getAllReminders() {
        return reminderEntryService.getAllReminderEntry();
    }


    @PostMapping
    public Reminder saveReminder(@RequestBody Reminder reminder) {
        return reminderEntryService.saveReminderEntry(reminder);
    }


    @GetMapping("/guild/{victGuild}")
    public Optional<Reminder> getReminder(@PathVariable VictGuild victGuild) {
        return reminderEntryService.getReminderEntryByVictGuild(victGuild);
    }

}
