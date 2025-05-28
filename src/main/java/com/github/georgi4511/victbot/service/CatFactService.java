package com.github.georgi4511.victbot.service;

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
    ResponseEntity<CatFactRes> response =
        restTemplate.getForEntity(CAT_FACT_API_URL, CatFactRes.class);
    HttpStatusCode responseStatusCode = response.getStatusCode();
    CatFactRes responseBody = response.getBody();
    if (responseStatusCode.is2xxSuccessful() && responseBody != null) {
      return responseBody.fact();
    }
    return "Sorry, I couldn't fetch a cat fact right now.";
  }

  private record CatFactRes(String fact) {}
}
