package com.github.georgi4511.victbot.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class CatFactService {
    private static final String CAT_FACT_API_URL = "https://catfact.ninja/fact";
    private final RestTemplate restTemplate;

    public String getRandomCatFact() {
        ResponseEntity<CatFactDto> response = restTemplate.getForEntity(CAT_FACT_API_URL, CatFactDto.class);
        HttpStatusCode responseStatusCode = response.getStatusCode();
        CatFactDto responseBody = response.getBody();
        if (responseStatusCode.is2xxSuccessful() && !isNull(responseBody)) {
            return responseBody.getFact();
        }
        return "Sorry, I couldn't fetch a cat fact right now.";
    }

    @Data
    private static class CatFactDto {
        String fact;
    }
}