package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.entity.dto.ImpressionsDto;
import com.github.georgi4511.victbot.service.ImpressionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/impressions")
@RequiredArgsConstructor
public class ImpressionController {

    private final ImpressionsService impressionsService;

    @GetMapping
    public List<ImpressionsDto> getAllImpressions() {
        return impressionsService.getAllImpressions().stream().map(ImpressionsDto::new).toList();
    }


    @PostMapping
    public ImpressionsDto createImpressions(@RequestBody Impressions impressions) {
        return ImpressionsDto.from(impressionsService.saveImpressions(impressions));
    }


    @GetMapping("/guild/{victGuild}")
    public Optional<ImpressionsDto> getImpressions(@PathVariable VictGuild victGuild) {
        return ImpressionsDto.from(impressionsService.getImpressionsByVictGuild(victGuild));
    }

    @PostMapping("/create")
    public ImpressionsDto createImpressions(@RequestBody VictGuild victGuild) {
        return ImpressionsDto.from(impressionsService.saveImpressions(victGuild));
    }

}
