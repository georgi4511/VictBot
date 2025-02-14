package com.github.georgi4511.victbot.entity.dto;

import com.github.georgi4511.victbot.entity.Impressions;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ImpressionsDto {

    Long id;
    Integer goodBotCount;
    Integer badBotCount;
    String discordId;

    public ImpressionsDto(Impressions impressions) {
        id = impressions.getId();
        goodBotCount = impressions.getGoodBotCount();
        badBotCount = impressions.getBadBotCount();
        discordId = impressions.getVictGuild().getDiscordId();
    }

    public static ImpressionsDto from(Impressions impressions) {
        return new ImpressionsDto(impressions);
    }

    public static Optional<ImpressionsDto> from(Optional<Impressions> impressions) {
        return impressions.map(ImpressionsDto::from);
    }


}
