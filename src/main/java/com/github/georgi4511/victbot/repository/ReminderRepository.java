/* (C)2025 */
package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.Reminder;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.entity.VictUser;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface ReminderRepository extends ListCrudRepository<Reminder, Long> {
  Optional<Reminder> findByVictGuild(VictGuild victGuildId);

  Optional<Reminder> findByVictUser(VictUser victUser);
}
