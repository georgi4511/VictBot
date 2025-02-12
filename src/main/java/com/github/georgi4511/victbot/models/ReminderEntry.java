package com.github.georgi4511.victbot.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class ReminderEntry {
    @NonNull
    String createdTime;
    @NonNull
    String targetTime;
    @NonNull
    String userId;
    @NonNull
    String message;
    @NonNull
    String channelSentFrom;
    // TODO idea could also be send to DM per request param maybe
}
