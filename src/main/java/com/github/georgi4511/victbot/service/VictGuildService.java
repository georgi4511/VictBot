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

    public VictGuild findVictGuildByDiscordIdOrCreate(@NonNull String discordId) {
        return victGuildRepository.findByDiscordId(discordId).orElseGet(() -> victGuildRepository.saveAndFlush(new VictGuild(discordId)));
    }

    public VictGuild saveVictGuild(VictGuild victGuild) {
        return victGuildRepository.save(victGuild);
    }

    @Transactional
    public Integer incrementBadBotCount(VictGuild victGuild) {
        Impressions impressions = victGuild.getImpressions();
        if (isNull(impressions)) {
            impressions = new Impressions();
        }
        int i = impressions.getBadBotCount() + 1;

        impressions.setBadBotCount(i);
        victGuild.setImpressions(impressions);
        victGuildRepository.save(victGuild);
        return i;
    }

//    @Transactional
//    public Integer incrementGoodBot(@NonNull String discordId) {
//        VictGuild victGuild = findVictGuildByDiscordIdOrCreate(discordId);
//        Impressions impressions = victGuild.getImpressions();
//        Integer goodBotCount = impressions.getGoodBotCount();
//        goodBotCount++;
//        impressions.setGoodBotCount(goodBotCount);
//        victGuild.setImpressions(impressions);
//        victGuildRepository.save(victGuild);
//        return goodBotCount;
//    }
//
//    @Transactional
//    public Integer incrementBadBot(@NonNull String discordId) {
//        VictGuild victGuild = findVictGuildByDiscordIdOrCreate(discordId);
//        Impressions impressions = victGuild.getImpressions();
//        Integer badBotCount = impressions.getBadBotCount();
//        badBotCount++;
//        impressions.setBadBotCount(badBotCount);
//        victGuild.setImpressions(impressions);
//        victGuildRepository.save(victGuild);
//        return badBotCount;
//    }

}
