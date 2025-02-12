package com.github.georgi4511.victbot.models;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class ReminderJson extends BaseJson {
    private String createdTime;
    private String targetTime;
    private String userId;
    private String message;
    private String channelSentFrom;

    public ReminderJson() throws IOException {
        super("reminder", "[]");

    }

}
