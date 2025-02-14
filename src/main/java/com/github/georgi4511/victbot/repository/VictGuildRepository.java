package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.VictGuild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VictGuildRepository extends JpaRepository<VictGuild, Long> {
    Optional<VictGuild> findByDiscordId(String discordId);
}
