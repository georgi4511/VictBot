package com.github.georgi4511.victbot.services;

import com.github.georgi4511.victbot.dtos.CatPictureDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CatPicService {
    private final RestTemplate restTemplate;
    private static final String CAT_PIC_API_URL = "https://api.thecatapi.com/v1/images/search";

    public String getRandomCatPicture() {
        ResponseEntity<CatPictureDto[]> response = restTemplate.getForEntity(CAT_PIC_API_URL, CatPictureDto[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody()[0].getUrl();
        } else {
            throw new RuntimeException("Failed to fetch cat fact");
        }
    }
}
