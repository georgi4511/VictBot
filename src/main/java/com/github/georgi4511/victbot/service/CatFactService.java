/* (C)2025 */
package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.service.dto.CatFactDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatFactService {
    private static final String CAT_FACT_API_URL = "https://catfact.ninja/fact";
    private final RestTemplate restTemplate;

    public CatFactService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRandomCatFact() {
        ResponseEntity<CatFactDto> response =
                restTemplate.getForEntity(CAT_FACT_API_URL, CatFactDto.class);
        HttpStatusCode responseStatusCode = response.getStatusCode();
        CatFactDto responseBody = response.getBody();
        if (responseStatusCode.is2xxSuccessful() && responseBody != null) {
            return responseBody.getFact();
        }
        return "Sorry, I couldn't fetch a cat fact right now.";
    }


}
