/* (C)2025 */
package com.github.georgi4511.victbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.georgi4511.victbot.service.dto.CatFactDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class CatFactServiceTest {

  public static final String URL = "https://catfact.ninja/fact";
  @Mock RestTemplate restTemplate = mock(RestTemplate.class);

  private CatFactService unitTestService;

  @BeforeEach
  public void setup() {
    unitTestService = new CatFactService(restTemplate);
  }

  @Test
  void getRandomCatFact_ok() {

    // given
    String exampleFact = "example_fact";
    CatFactDto result = new CatFactDto(exampleFact);

    // when
    ResponseEntity<CatFactDto> catFactDtoResponseEntity =
        new ResponseEntity<>(result, HttpStatus.OK);
    when(restTemplate.getForEntity(URL, CatFactDto.class)).thenReturn(catFactDtoResponseEntity);

    String randomCatFact = unitTestService.getRandomCatFact();

    // then
    assertEquals(exampleFact, randomCatFact);
  }

  @Test
  void getRandomCatFact_failed() {

    // when
    String exampleFact = "example_fact";
    CatFactDto result = new CatFactDto(exampleFact);

    // then
    ResponseEntity<CatFactDto> catFactDtoResponseEntity =
        new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    when(restTemplate.getForEntity(URL, CatFactDto.class)).thenReturn(catFactDtoResponseEntity);

    String randomCatFact = unitTestService.getRandomCatFact();

    // expect
    assertEquals("Sorry, I couldn't fetch a cat fact right now.", randomCatFact);
  }

  @Test
  void getRandomCatFact_null_failed() {
    // then
    ResponseEntity<CatFactDto> catFactDtoResponseEntity = new ResponseEntity<>(null, HttpStatus.OK);
    when(restTemplate.getForEntity(URL, CatFactDto.class)).thenReturn(catFactDtoResponseEntity);

    String randomCatFact = unitTestService.getRandomCatFact();

    // expect
    assertEquals("Sorry, I couldn't fetch a cat fact right now.", randomCatFact);
  }
}
