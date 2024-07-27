package com.github.georgi4511.discord_bot.jsonService;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Map;

@NonNull
@Getter
@Setter
@Slf4j
public class BotImpressionsJSON extends BaseJSON{
    private Integer badBotCount;
    private Integer goodBotCount;
    private static final String NAME = "botImpressions";
    private static final String DEFAULT_CONTENT = new Gson().toJson(Map.of("badBotCount", 0,"goodBotCount",0));

    public BotImpressionsJSON() throws IOException {
        super(NAME, DEFAULT_CONTENT);

    }
}
