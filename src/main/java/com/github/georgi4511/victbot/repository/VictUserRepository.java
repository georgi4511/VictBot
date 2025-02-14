package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.VictUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VictUserRepository extends JpaRepository<VictUser, Long> {
    Optional<VictUser> findByDiscordId(String discordId);
}
