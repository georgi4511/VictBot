package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictGuildImpressions;
import com.github.georgi4511.victbot.repository.VictGuildRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VictGuildService {
  final VictGuildRepository victGuildRepository;

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
    try {
      Optional<VictGuild> existing = victGuildRepository.findById(victGuildId);
      if (existing.isPresent()) {
        return existing.get();
      }

      VictGuild newEntity = new VictGuild();
      newEntity.setId(victGuildId);
      return victGuildRepository.save(newEntity);
    } catch (DataIntegrityViolationException e) {
      return victGuildRepository
          .findById(victGuildId)
          .orElseThrow(() -> new RuntimeException("Failed to get or create entity"));
    }
  }

  public boolean existsById(@NonNull String discordId) {
    return victGuildRepository.existsById(discordId);
  }

  public VictGuild save(VictGuild victGuild) {
    return victGuildRepository.save(victGuild);
  }

  public Optional<VictGuild> findById(String id) {
    return victGuildRepository.findById(id);
  }

  public Long incrementBadBotImpressions(String id) {
    VictGuild victGuild = findByIdOrCreate(id);
    victGuild.setBadBotImpressions(victGuild.getBadBotImpressions() + 1);
    return victGuildRepository.save(victGuild).getBadBotImpressions();
  }

  public Long incrementGoodBotImpressions(String id) {
    VictGuild victGuild = findByIdOrCreate(id);
    victGuild.setGoodBotImpressions(victGuild.getGoodBotImpressions() + 1);
    return victGuildRepository.save(victGuild).getGoodBotImpressions();
  }
}
