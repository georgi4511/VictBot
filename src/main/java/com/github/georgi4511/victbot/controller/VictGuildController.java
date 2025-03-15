package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.VictGuildImpressions;
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

  @GetMapping("impressions")
  public List<VictGuildImpressions> getAllVictGuildImpressions() {
    return victGuildService.findAllImpressions();
  }

  @GetMapping("impressions/{id}")
  public VictGuildImpressions getImpressionsById(@PathVariable String id) {
    return victGuildService.findImpressionsById(id);
  }

  @PostMapping("create")
  public VictGuild saveVictGuild(@RequestBody VictGuild victGuild) {
    return victGuildService.create(victGuild);
  }

  @PostMapping("create/id")
  public VictGuild saveVictGuild(@RequestBody String id) {
    return victGuildService.create(id);
  }

  @GetMapping("id/{id}")
  public Optional<VictGuild> getVictGuild(@PathVariable String id) {
    return victGuildService.findById(id);
  }
}
