package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.entity.VictUser;
import com.github.georgi4511.victbot.repository.VictUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VictUserService {
    final VictUserRepository victUserRepository;

    public List<VictUser> getAllVictUsers() {
        return victUserRepository.findAll();
    }

    public VictUser getVictUserByDiscordIdOrCreate(@NonNull String discordId) {
        return victUserRepository.findByDiscordId(discordId).orElseGet(() -> victUserRepository.save(new VictUser(discordId)));
    }

    public boolean victUserExists(VictUser victUser) {
        return victUserRepository.existsById(victUser.getId());
    }

    public boolean existsVictUserByDiscordId(String discordId) {
        return victUserRepository.existsVictUserByDiscordId(discordId);
    }


    public void saveVictUser(VictUser victUser) {
        victUserRepository.save(victUser);
    }
}
