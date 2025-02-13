package com.github.georgi4511.victbot.controller;

import com.github.georgi4511.victbot.entity.Impressions;
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
    public List<Impressions> getAllImpressions() {
        return impressionsService.getAllImpressions();
    }


    @PostMapping
    public Impressions createImpressions(@RequestBody Impressions impressions) {
        return impressionsService.saveImpressions(impressions);
    }


    @GetMapping("/guild/{guildId}")
    public Optional<Impressions> getImpressions(@PathVariable String guildId) {
        return impressionsService.getImpressionsByGuildId(guildId);
    }

    @PostMapping("/create")
    public Impressions createImpressions(@RequestBody String guildId) {
        return impressionsService.saveImpressions(guildId);
    }

}
