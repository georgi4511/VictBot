package com.github.georgi4511.victbot.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class BotImpressions {
    @NonNull
    Integer badBodCount = 0;
    @NonNull
    Integer goodBotCount = 0;
}
