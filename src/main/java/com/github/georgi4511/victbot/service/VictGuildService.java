/* (C)2025 */
package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.repository.VictGuildRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VictGuildService {
    final VictGuildRepository victGuildRepository;

    public List<VictGuild> getAllVictGuilds() {
        return victGuildRepository.findAll();
    }

    @Transactional
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
