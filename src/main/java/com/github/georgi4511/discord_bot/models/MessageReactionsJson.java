package com.github.georgi4511.discord_bot.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
@NonNull
public class MessageReactionsJson extends BaseJson {
    private String userId;
    private String message;
    private String createdTime;
    private String response;
    private String guildId;

    public MessageReactionsJson() throws IOException {
        super("messageReactions","[]");
    }
}
