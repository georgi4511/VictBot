package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictGuildImpressions;
import com.github.georgi4511.victbot.repository.VictGuildRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VictGuildService {

  private final VictGuildRepository victGuildRepository;

  public List<VictGuild> findAll() {
    return victGuildRepository.findAll();
  }

  public List<VictGuildImpressions> findAllImpressions() {
    return victGuildRepository.findBy(VictGuildImpressions.class);
  }

  public VictGuildImpressions findImpressionsById(String id) {
    return victGuildRepository.findById(VictGuildImpressions.class, id);
  }

  public VictGuild findByIdOrCreate(@NonNull String victGuildId) {
    Optional<VictGuild> optionalVictGuild = victGuildRepository.findById(victGuildId);

    if (optionalVictGuild.isPresent()) {
      return optionalVictGuild.get();
    }

    VictGuild victGuild = new VictGuild(victGuildId);
    return victGuildRepository.save(victGuild);
  }

  public boolean existsById(@NonNull String discordId) {
    return victGuildRepository.existsById(discordId);
  }

  public VictGuild create(VictGuild victGuild) {
    return victGuildRepository.save(victGuild);
  }

  public VictGuild create(String id) {
    VictGuild victGuild = new VictGuild(id);
    return victGuildRepository.save(victGuild);
  }

  public Optional<VictGuild> findById(String id) {
    return victGuildRepository.findById(id);
  }

  public Long incrementBadBotImpressions(String id) {
    VictGuild victGuild = findByIdOrCreate(id);
    return victGuildRepository.save(victGuild.incrementBadImpressions(1L)).getBadBotImpressions();
  }

  public Long incrementGoodBotImpressions(String id) {
    VictGuild victGuild = findByIdOrCreate(id);
    return victGuildRepository.save(victGuild.incrementGoodImpressions(1L)).getGoodBotImpressions();
  }
}
