package com.github.georgi4511.victbot.models;

import lombok.Data;

@Data
public class ReminderEntry {
    String createdTime;
    String targetTime;
    String userId;
    String message;
    String channelSentFrom;
    // TODO idea could also be send to DM per request param maybe
}
