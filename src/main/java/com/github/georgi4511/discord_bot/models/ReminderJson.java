package com.github.georgi4511.discord_bot.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;

@NonNull
@Getter
@Setter
public class ReminderJson extends BaseJson {
    private String createdTime;
    private String targetTime;
    private String userId;
    private String message;
    private String channelSentFrom;

    public ReminderJson()  throws IOException {
        super("reminder","[]");

    }

}
