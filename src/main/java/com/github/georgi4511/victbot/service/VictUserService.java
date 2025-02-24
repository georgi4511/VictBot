package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.repository.VictUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VictUserService {
    final VictUserRepository victUserRepository;

    public List<VictUser> getAllVictUsers() {
        return victUserRepository.findAll();
    }

    public VictUser getVictUserByDiscordIdOrCreate(@NonNull String discordId) {
        return victUserRepository
                .findByDiscordId(discordId)
                .orElseGet(() -> victUserRepository.save(new VictUser(discordId)));
    }

    public boolean victUserExists(VictUser victUser) {
        return victUserRepository.existsById(victUser.getId());
    }

    public boolean existsVictUserByDiscordId(String discordId) {
        return victUserRepository.existsVictUserByDiscordId(discordId);
    }

    public VictUser saveVictUser(VictUser victUser) {
        return victUserRepository.save(victUser);
    }

    public Optional<VictUser> getByVictUserId(Long id) {
        return victUserRepository.findById(id);
    }

    public Optional<VictUser> getByVictUserDiscordId(String discordId) {
        return victUserRepository.findByDiscordId(discordId);
    }
}
