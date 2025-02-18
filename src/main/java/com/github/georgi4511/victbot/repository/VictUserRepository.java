/* (C)2025 */
package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.VictUser;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface VictUserRepository extends ListCrudRepository<VictUser, Long> {
  Optional<VictUser> findByDiscordId(String discordId);

  Boolean existsVictUserByDiscordId(String discordId);
}
