package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.Reminder;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.entity.VictUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findByVictGuild(VictGuild victGuildId);

    Optional<Reminder> findByVictUser(VictUser victUser);
}
