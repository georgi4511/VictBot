package com.github.georgi4511.victbot.model.dto;

import com.github.georgi4511.victbot.model.Impressions;

public record ImpressionsDto(Integer goodBotCount, Integer badBotCount, String discordId) {
    public static ImpressionsDto fromImpressions(Impressions impressions) {
        return new ImpressionsDto(
                impressions.getGoodBotCount(),
                impressions.getBadBotCount(),
                impressions.getVictGuild().getDiscordId());
    }
}
