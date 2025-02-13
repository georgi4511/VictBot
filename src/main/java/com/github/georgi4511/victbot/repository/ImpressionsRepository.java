package com.github.georgi4511.victbot.repository;

import com.github.georgi4511.victbot.entity.Impressions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImpressionsRepository extends JpaRepository<Impressions, Long> {
    Optional<Impressions> findByGuildId(String guildId);
}
