package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.service.VictGuildService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guilds")
@RequiredArgsConstructor
public class VictGuildController {

  private final VictGuildService victGuildService;

  @GetMapping
  public List<VictGuild> getAllVictGuilds() {
    return victGuildService.findAll();
  }

  @PostMapping
  public VictGuild saveVictGuild(@RequestBody VictGuild reminder) {
    return victGuildService.save(reminder);
  }

  @GetMapping("id/{id}")
  public Optional<VictGuild> getVictGuild(@PathVariable String id) {
    return victGuildService.findById(id);
  }
}
