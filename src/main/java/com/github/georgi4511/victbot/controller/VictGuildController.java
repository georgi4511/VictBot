package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.service.VictGuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guilds")
@RequiredArgsConstructor
public class VictGuildController {

    private final VictGuildService victGuildService;

    @GetMapping
    public List<VictGuild> getAllVictGuilds() {
        return victGuildService.getAllVictGuilds();
    }


    @PostMapping
    public VictGuild saveVictGuild(@RequestBody VictGuild reminder) {
        return victGuildService.saveVictGuild(reminder);
    }


    @GetMapping("/{id}")
    public Optional<VictGuild> getVictGuild(@PathVariable Long id) {
        return victGuildService.getByVictGuildId(id);
    }

    @GetMapping("/{discordId}")
    public Optional<VictGuild> getVictGuild(@PathVariable String discordId) {
        return victGuildService.getByVictGuildDiscordId(discordId);
    }

}
