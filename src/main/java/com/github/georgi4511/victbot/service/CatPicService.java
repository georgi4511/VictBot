package com.github.georgi4511.victbot.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class CatPicService {
    private static final String CAT_PIC_API_URL = "https://api.thecatapi.com/v1/images/search";
    private final RestTemplate restTemplate;

    public String getRandomCatPicture() {
        ResponseEntity<CatPicturesDto> response = restTemplate.getForEntity(CAT_PIC_API_URL, CatPicturesDto.class);
        CatPicturesDto responseBody = response.getBody();
        if (response.getStatusCode().is2xxSuccessful() && !isNull(responseBody)) {
            return responseBody.getCatPictureDtoList().getFirst().getUrl();
        }
        return "Sorry, I couldn't fetch a cat picture right now.";
    }

    @Data
    private static class CatPictureDto {
        String id;
        String url;

    }

    @Data
    private static class CatPicturesDto {
        List<CatPictureDto> catPictureDtoList;
    }

}
