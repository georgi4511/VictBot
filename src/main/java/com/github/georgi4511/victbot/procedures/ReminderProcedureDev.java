package com.github.georgi4511.victbot.procedures;

import com.github.georgi4511.victbot.model.Reminder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"default", "dev"})
@Service
@RequiredArgsConstructor
public class ReminderProcedureDev implements ReminderProcedure {
  @Override
  public void handleReminders() {
    throw new IllegalStateException("Not Implemented");
  }

  @Override
  public void sendReminder(Reminder reminder) {
    throw new IllegalStateException("Not Implemented");
  }
}
