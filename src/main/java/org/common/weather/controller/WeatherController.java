package org.common.weather.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.common.weather.util.WeatherFeignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class WeatherController {

    @Autowired
    private WeatherFeignUtil weatherFeignUtil;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value( "${project.api.key}" )
    private String apiKey;

    @Operation(summary = "get current weather condition from certain location")
    @GetMapping(value = "/getWeatherByCity/{cityName}", produces = "application/json")
    public ResponseEntity<String> getWeatherByCity(@PathVariable String cityName) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", cityName)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build().toUri();
        try {
            return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.internalServerError().body(ex.getResponseBodyAsString());
        }
    }

    @GetMapping(value = "/getWeather/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getWeather(@PathVariable String city) {
        return ResponseEntity.ok().body(weatherFeignUtil.getCurrentWeather(city, apiKey, "metric"));
    }

}
