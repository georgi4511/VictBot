package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.model.VictUser;
import com.github.georgi4511.victbot.service.VictUserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class VictUserController {

  private final VictUserService victUserService;

  @GetMapping
  public List<VictUser> getAllVictUsers() {
    return victUserService.findAllVictUsers();
  }

  @PostMapping("/create")
  public VictUser saveVictUser(@RequestBody VictUser victUser) {
    return victUserService.create(victUser);
  }

  @PostMapping("/create/id")
  public VictUser saveVictUser(@RequestBody String id) {
    return victUserService.create(id);
  }

  @GetMapping("/{id}")
  public Optional<VictUser> getVictUser(@PathVariable String id) {
    return victUserService.findById(id);
  }
}
