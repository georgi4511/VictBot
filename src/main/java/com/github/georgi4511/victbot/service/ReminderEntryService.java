package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.Reminder;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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
