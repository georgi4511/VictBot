package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.model.Impressions;
import com.github.georgi4511.victbot.model.VictGuild;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

public interface ImpressionsRepository extends ListCrudRepository<Impressions, Long> {
  Optional<Impressions> findByVictGuild(VictGuild victGuild);
}
