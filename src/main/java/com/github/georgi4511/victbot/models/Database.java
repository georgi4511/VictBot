package com.github.georgi4511.victbot.models;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class Database {
    @NonNull
    private List<MessageCreateEntry> messageCreateEntryList;
    @NonNull
    private List<ReminderEntry> reminderEntryList;
    @NonNull
    private BotImpressions botImpressions;

    public Database() {
        messageCreateEntryList = List.of();
        reminderEntryList = List.of();
        botImpressions = new BotImpressions();
    }

}
