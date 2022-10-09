package org.common.weather.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class WeatherControllerTest {
    @MockBean
    RestTemplate restTemplate;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;
    @Value("${project.api.key}")
    private String apiKey;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("expect http status code 200 when param CityName is right")
    void success_call_with_right_city() {
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok(""));
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", "Bandung")
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build().toUri();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
        assertEquals(ResponseEntity.ok(""), exchange);
    }

    @Test
    @DisplayName("expect http status code 500 when param CityName is wrong")
    void not_success_call_with_wrong_city() {
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", "Wrong_City")
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build().toUri();
        ResponseEntity<String> exchange = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
        assertEquals(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(), exchange);
    }

}