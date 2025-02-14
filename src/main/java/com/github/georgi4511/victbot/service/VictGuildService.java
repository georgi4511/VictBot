package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.repository.VictGuildRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class VictGuildService {
    final VictGuildRepository victGuildRepository;

    public List<VictGuild> getAllVictGuilds() {
        return victGuildRepository.findAll();
    }

    @Transactional
    public VictGuild findVictGuildByDiscordIdOrCreate(@NonNull String discordId) {
        return victGuildRepository.findByDiscordId(discordId).orElseGet(() -> victGuildRepository.saveAndFlush(new VictGuild(discordId)));
    }

    public void saveVictGuild(VictGuild victGuild) {
        victGuildRepository.save(victGuild);
    }


    @Transactional
    public void addImpressionsToGuild(VictGuild victGuild) {
        Impressions impressions = new Impressions(victGuild);
        victGuild.setImpressions(impressions);
        victGuildRepository.save(victGuild);
    }

    @Transactional
    public Integer incrementBadBotCount(VictGuild victGuild) {
        Impressions impressions = victGuild.getImpressions();
        if (isNull(impressions)) {
            impressions = new Impressions();
        }
        int result = impressions.getBadBotCount() + 1;

        impressions.setBadBotCount(result);
        victGuild.setImpressions(impressions);
        victGuildRepository.save(victGuild);
        return result;
    }

    @Transactional
    public Integer incrementGoodBotCount(VictGuild victGuild) {
        Impressions impressions = victGuild.getImpressions();
        if (isNull(impressions)) {
            impressions = new Impressions();
        }
        int result = impressions.getGoodBotCount() + 1;

        impressions.setGoodBotCount(result);
        victGuild.setImpressions(impressions);
        victGuildRepository.save(victGuild);
        return result;
    }
}
