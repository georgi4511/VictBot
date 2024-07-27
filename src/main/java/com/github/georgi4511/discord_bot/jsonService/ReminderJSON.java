package com.github.georgi4511.discord_bot.jsonService;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;

@NonNull
@Getter
@Setter
public class ReminderJSON extends BaseJSON{
    private String createdTime;
    private String targetTime;
    private String userId;
    private String message;
    private String channelSentFrom;

    public ReminderJSON()  throws IOException {
        super("reminder","[]");

    }

}
