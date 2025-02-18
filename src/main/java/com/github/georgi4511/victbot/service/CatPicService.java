/* (C)2025 */
package com.github.georgi4511.victbot.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CatPicService {
    private static final String CAT_PIC_API_URL = "https://api.thecatapi.com/v1/images/search";
    private static final Logger log = LoggerFactory.getLogger(CatPicService.class);
    private final RestTemplate restTemplate;

    public String getRandomCatPicture() {
        try {
            CatPictureDto[] catPicturesDto =
                    restTemplate.getForObject(CAT_PIC_API_URL, CatPictureDto[].class);
            if (catPicturesDto != null && catPicturesDto.length > 0) {
                return catPicturesDto[0].url();
            }
        } catch (RestClientException e) {
            log.error("Failed to fetch cat picture from cat api at: {}", CAT_PIC_API_URL, e);
        }
        return "Sorry, I couldn't fetch a cat picture right now.";
    }

    record CatPictureDto(String id, String url, Integer width, Integer height) {}
}
