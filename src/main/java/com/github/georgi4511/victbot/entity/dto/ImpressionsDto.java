/* (C)2025 */
package com.github.georgi4511.victbot.entity.dto;

import com.github.georgi4511.victbot.entity.Impressions;

public record ImpressionsDto(Long id, Integer goodBotCount, Integer badBotCount, String discordId) {
    public static ImpressionsDto fromImpressions(Impressions impressions) {
        return new ImpressionsDto(
                impressions.getId(),
                impressions.getGoodBotCount(),
                impressions.getBadBotCount(),
                impressions.getVictGuild().getDiscordId());
    }
}
