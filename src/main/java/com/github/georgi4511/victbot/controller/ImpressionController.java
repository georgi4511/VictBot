package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.entity.Impressions;
import com.github.georgi4511.victbot.entity.VictGuild;
import com.github.georgi4511.victbot.entity.dto.ImpressionsDto;
import com.github.georgi4511.victbot.service.ImpressionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/impressions")
@RequiredArgsConstructor
public class ImpressionController {

    private final ImpressionsService impressionsService;

    @GetMapping
    public List<ImpressionsDto> getAllImpressions() {
        return impressionsService.getAllImpressions().stream().map((ImpressionsDto::fromImpressions)).toList();
    }


    @PostMapping
    public ImpressionsDto saveImpressions(@RequestBody Impressions impressions) {
        return ImpressionsDto.fromImpressions(impressionsService.saveImpressions(impressions));
    }


    @GetMapping("/guild/{victGuild}")
    public ImpressionsDto getImpressions(@PathVariable VictGuild victGuild) {
        return impressionsService
                .getImpressionsByVictGuild(victGuild)
                .map(ImpressionsDto::fromImpressions)
                .orElse(new ImpressionsDto(null, 0, 0, null));
    }

    @PostMapping("/create")
    public ImpressionsDto saveImpressions(@RequestBody VictGuild victGuild) {
        return ImpressionsDto.fromImpressions(impressionsService.saveImpressions(victGuild));
    }

}
