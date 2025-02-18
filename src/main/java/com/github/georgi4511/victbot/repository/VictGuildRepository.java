/* (C)2025 */
package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.VictGuild;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface VictGuildRepository extends ListCrudRepository<VictGuild, Long> {
  Optional<VictGuild> findByDiscordId(String discordId);

  Boolean existsVictGuildByDiscordId(String discordId);
}
