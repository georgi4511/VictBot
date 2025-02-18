package com.github.georgi4511.victbot.service;

import com.github.georgi4511.victbot.service.dto.CatFactDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CatFactServiceTest {

    @Mock
    RestTemplate restTemplate = mock(RestTemplate.class);

    private CatFactService unitTestService;

    @BeforeEach
    public void setup() {
        unitTestService = new CatFactService(restTemplate);
    }

    @Test
    void getRandomCatFact_ok() {

        //when
        String exampleFact = "example_fact";
        CatFactDto result = new CatFactDto(exampleFact);

        //then
        ResponseEntity<CatFactDto> catFactDtoResponseEntity = new ResponseEntity<>(result, HttpStatus.OK);
        when(restTemplate.getForEntity("https://catfact.ninja/fact", CatFactDto.class)).thenReturn(catFactDtoResponseEntity);

        String randomCatFact = unitTestService.getRandomCatFact();

        //expect
        assertEquals(exampleFact, randomCatFact);
    }

    @Test
    void getRandomCatFact_failed() {

        //when
        String exampleFact = "example_fact";
        CatFactDto result = new CatFactDto(exampleFact);

        //then
        ResponseEntity<CatFactDto> catFactDtoResponseEntity = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn((ResponseEntity) catFactDtoResponseEntity);

        String randomCatFact = unitTestService.getRandomCatFact();

        //expect
        assertEquals("Sorry, I couldn't fetch a cat fact right now.", randomCatFact);
    }

    @Test
    void getRandomCatFact_null_failed() {
        //then
        ResponseEntity<CatFactDto> catFactDtoResponseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), any())).thenReturn((ResponseEntity) catFactDtoResponseEntity);

        String randomCatFact = unitTestService.getRandomCatFact();

        //expect
        assertEquals("Sorry, I couldn't fetch a cat fact right now.", randomCatFact);
    }

}