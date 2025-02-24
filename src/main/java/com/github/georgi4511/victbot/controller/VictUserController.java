package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.service.VictUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class VictUserController {

    private final VictUserService victUserService;

    @GetMapping
    public List<VictUser> getAllVictUsers() {
        return victUserService.getAllVictUsers();
    }

    @PostMapping
    public VictUser saveVictUser(@RequestBody VictUser reminder) {
        return victUserService.saveVictUser(reminder);
    }

    @GetMapping("/{id}")
    public Optional<VictUser> getVictUser(@PathVariable Long id) {
        return victUserService.getByVictUserId(id);
    }

    @GetMapping("/{discordId}")
    public Optional<VictUser> getVictUser(@PathVariable String discordId) {
        return victUserService.getByVictUserDiscordId(discordId);
    }
}
