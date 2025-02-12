package com.github.georgi4511.victbot.models;

import lombok.Data;

@Data
public class MessageCreateEntry {
    String userId;
    String createdTime;
    String message;
    String response;
    String guildId;

}