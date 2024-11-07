package com.github.georgi4511.discord_bot.models;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.io.IOException;
import java.util.Map;

@NonNull
@Getter
@Setter
public class BotImpressionsJson extends BaseJson {
    private Integer badBotCount;
    private Integer goodBotCount;
    private static final String NAME = "botImpressions";
    private static final String DEFAULT_CONTENT = new Gson().toJson(Map.of("badBotCount", 0,"goodBotCount",0));

    public BotImpressionsJson() throws IOException {
        super(NAME, DEFAULT_CONTENT);

    }
}
