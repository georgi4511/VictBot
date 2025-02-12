package com.github.georgi4511.victbot.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class MessageCreateEntry {
    @NonNull
    String userId;
    @NonNull
    String createdTime;
    @NonNull
    String message;
    @NonNull
    String response;
    @NonNull
    String guildId;

}