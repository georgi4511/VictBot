package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.entity.VictGuild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImpressionsRepository extends JpaRepository<Impressions, Long> {
    Optional<Impressions> findByVictGuild(VictGuild victGuild);
}
