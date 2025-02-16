package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.VictGuild;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VictGuildRepository extends ListCrudRepository<VictGuild, Long> {
    Optional<VictGuild> findByDiscordId(String discordId);

    Boolean existsVictGuildByDiscordId(String discordId);
}
