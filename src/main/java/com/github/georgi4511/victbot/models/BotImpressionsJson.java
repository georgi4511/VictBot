package com.github.georgi4511.victbot.models;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Map;

@Getter
@Setter
public class BotImpressionsJson extends BaseJson {
    private static final String NAME = "botImpressions";
    private static final String DEFAULT_CONTENT = new Gson().toJson(Map.of("badBotCount", 0, "goodBotCount", 0));
    private Integer badBotCount;
    private Integer goodBotCount;

    public BotImpressionsJson() throws IOException {
        super(NAME, DEFAULT_CONTENT);

    }
}
