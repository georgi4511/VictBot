package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.repository.VictUserRepository;
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
public class VictUserService {
  final VictUserRepository victUserRepository;

  public List<VictUser> findAllVictUsers() {
    return victUserRepository.findAll();
  }

  public boolean existsById(String id) {
    return victUserRepository.existsById(id);
  }

  public VictUser save(VictUser victUser) {
    return victUserRepository.save(victUser);
  }

  public Optional<VictUser> findById(String id) {
    return victUserRepository.findById(id);
  }

  public VictUser findByIdOrCreate(@NonNull String victUserId) {
    try {
      Optional<VictUser> existing = victUserRepository.findById(victUserId);
      if (existing.isPresent()) {
        return existing.get();
      }

      VictUser newEntity = new VictUser();
      newEntity.setId(victUserId);

      return victUserRepository.save(newEntity);
    } catch (DataIntegrityViolationException e) {
      return victUserRepository
          .findById(victUserId)
          .orElseThrow(() -> new RuntimeException("Failed to get or create entity"));
    }
  }
}
