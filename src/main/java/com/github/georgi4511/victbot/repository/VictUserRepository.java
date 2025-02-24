package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.model.VictUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface VictUserRepository extends ListCrudRepository<VictUser, Long> {
    Optional<VictUser> findByDiscordId(String discordId);

    Boolean existsVictUserByDiscordId(String discordId);
}
