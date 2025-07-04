package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.model.Reminder;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface ReminderRepository extends ListCrudRepository<Reminder, Long> {
  List<Reminder> findByVictGuildId(String id);

  List<Reminder> findByVictUserId(String id);

  List<Reminder> findByVictUserIdAndMessage(String victUserId, String message);
}
