package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.repository.VictGuildRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VictGuildService {
    final VictGuildRepository victGuildRepository;

    public List<VictGuild> getAllVictGuilds() {
        return victGuildRepository.findAll();
    }

    public VictGuild findVictGuildByDiscordIdOrCreate(@NonNull String discordId) {
        return victGuildRepository
                .findByDiscordId(discordId)
                .orElseGet(() -> victGuildRepository.save(new VictGuild(discordId)));
    }

    public boolean existsVictGuildByDiscordId(@NonNull String discordId) {
        return victGuildRepository.existsVictGuildByDiscordId(discordId);
    }

    public VictGuild saveVictGuild(VictGuild victGuild) {
        return victGuildRepository.save(victGuild);
    }

    public Optional<VictGuild> getByVictGuildId(Long id) {
        return victGuildRepository.findById(id);
    }

    public Optional<VictGuild> getByVictGuildDiscordId(String discordId) {
        return victGuildRepository.findByDiscordId(discordId);
    }
}
