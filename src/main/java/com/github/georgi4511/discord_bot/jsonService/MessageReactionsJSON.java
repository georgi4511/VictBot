package com.github.georgi4511.discord_bot.jsonService;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
@NonNull
public class MessageReactionsJSON extends BaseJSON{
    private String userId;
    private String message;
    private String createdTime;
    private String response;
    private String guildId;

    public MessageReactionsJSON() throws IOException {
        super("messageReactions","[]");
    }
}
