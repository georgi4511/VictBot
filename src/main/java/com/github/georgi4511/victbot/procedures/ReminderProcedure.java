
package com.github.georgi4511.victbot.procedures;

import com.github.georgi4511.victbot.model.Reminder;


public interface ReminderProcedure {

    void handleReminders();

    void sendReminder(Reminder reminder);
}
