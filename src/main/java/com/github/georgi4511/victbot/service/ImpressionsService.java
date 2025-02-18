/* (C)2025 */
package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.repository.ImpressionsRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImpressionsService {
    final ImpressionsRepository impressionsRepository;
    final VictGuildService victGuildService;

    public List<Impressions> getAllImpressions() {
        return impressionsRepository.findAll();
    }

    public Optional<Impressions> getImpressionsByVictGuild(VictGuild victGuild) {
        return impressionsRepository.findByVictGuild(victGuild);
    }

    public Impressions saveImpressions(Impressions impressions) {
        return impressionsRepository.save(impressions);
    }

    public Impressions saveImpressions(VictGuild victGuild) {
        return impressionsRepository.save(new Impressions(victGuild));
    }

    @Transactional
    public Impressions incrementImpressionsByDiscordId(String discordId, boolean isGoodBot) {
        Impressions impressions = getImpressionsOrCreateByDiscordId(discordId);

        if (isGoodBot) {
            impressions.setGoodBotCount(impressions.getGoodBotCount() + 1);
        } else {
            impressions.setBadBotCount(impressions.getBadBotCount() + 1);
        }
        return impressionsRepository.save(impressions);
    }

    @Transactional
    public Impressions getImpressionsOrCreateByDiscordId(String discordId) {
        VictGuild victGuild = victGuildService.findVictGuildByDiscordIdOrCreate(discordId);
        return impressionsRepository
                .findByVictGuild(victGuild)
                .orElseGet(
                        () -> {
                            victGuild.setImpressions(new Impressions(victGuild));
                            return victGuildService.saveVictGuild(victGuild).getImpressions();
                        });
    }
}
