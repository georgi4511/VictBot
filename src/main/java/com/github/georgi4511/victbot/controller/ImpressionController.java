package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.model.Impressions;
import com.github.georgi4511.victbot.model.VictGuild;
import com.github.georgi4511.victbot.model.dto.ImpressionsDto;
import com.github.georgi4511.victbot.service.ImpressionsService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/impressions")
@RequiredArgsConstructor
public class ImpressionController {

  private final ImpressionsService impressionsService;

  @GetMapping
  public List<ImpressionsDto> getAllImpressions() {
    return impressionsService.getAllImpressions().stream()
        .map((ImpressionsDto::fromImpressions))
        .toList();
  }

  @PostMapping
  public ImpressionsDto saveImpressions(@RequestBody Impressions impressions) {
    return ImpressionsDto.fromImpressions(impressionsService.saveImpressions(impressions));
  }

  @GetMapping("/guild/{victGuild}")
  public Optional<ImpressionsDto> getImpressions(@PathVariable VictGuild victGuild) {
    return impressionsService
        .getImpressionsByVictGuild(victGuild)
        .map(ImpressionsDto::fromImpressions);
  }

  @PostMapping("/create")
  public ImpressionsDto saveImpressions(@RequestBody VictGuild victGuild) {
    return ImpressionsDto.fromImpressions(impressionsService.saveImpressions(victGuild));
  }
}
