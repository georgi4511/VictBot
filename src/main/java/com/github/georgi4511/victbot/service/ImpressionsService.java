package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.repository.ImpressionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImpressionsService {
    final ImpressionsRepository impressionsRepository;

    public List<Impressions> getAllImpressions() {
        return impressionsRepository.findAll();
    }

    public Optional<Impressions> getImpressionsByGuildId(String guildId) {
        return impressionsRepository.findByGuildId(guildId);
    }

    public Impressions saveImpressions(Impressions impressions) {
        return impressionsRepository.save(impressions);
    }

    public Impressions saveImpressions(String guildId) {

        Impressions impressions = new Impressions(guildId);
        return impressionsRepository.save(impressions);
    }
}
