package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findByGuildId(String guildId);
}
