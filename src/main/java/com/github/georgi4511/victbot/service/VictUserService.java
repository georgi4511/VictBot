package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.repository.VictUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VictUserService {

  private final VictUserRepository victUserRepository;

  public List<VictUser> findAllVictUsers() {
    return victUserRepository.findAll();
  }

  public boolean existsById(String id) {
    return victUserRepository.existsById(id);
  }

  public VictUser create(VictUser victUser) {
    return victUserRepository.save(victUser);
  }

  public VictUser create(String id) {
    VictUser victUser = new VictUser(id);
    return victUserRepository.save(victUser);
  }

  public Optional<VictUser> findById(String id) {
    return victUserRepository.findById(id);
  }
}
