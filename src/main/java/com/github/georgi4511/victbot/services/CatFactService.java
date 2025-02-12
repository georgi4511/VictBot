package com.github.georgi4511.victbot.services;

import com.github.georgi4511.victbot.dtos.CatFactDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CatFactService {
    private final RestTemplate restTemplate;
    private static final String CAT_FACT_API_URL = "https://catfact.ninja/fact";

    public String getRandomCatFact() {
        ResponseEntity<CatFactDto> response = restTemplate.getForEntity(CAT_FACT_API_URL, CatFactDto.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getFact();
        } else {
            throw new RuntimeException("Failed to fetch cat fact");
        }
    }
}