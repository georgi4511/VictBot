package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.entity.Reminder;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReminderEntryService {
    final ReminderRepository reminderRepository;

    public List<Reminder> getAllReminderEntry() {
        return reminderRepository.findAll();
    }

    public Optional<Reminder> getReminderEntryByVictGuild(VictGuild victGuild) {
        return reminderRepository.findByVictGuild(victGuild);
    }

    public Reminder saveReminderEntry(Reminder reminder) {
        return reminderRepository.save(reminder);
    }
}
