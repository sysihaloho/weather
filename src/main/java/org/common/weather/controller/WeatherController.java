package org.common.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class WeatherController {

    private static final String API_KEY = "AYjxgwAN1FVfG8Z7sJ2iaOLkyg1gB0H8";
    private static final String API_KEY_OPEN = "87ef1ce917d5ec0ae1f4ad1b8dd931d4";

    RestTemplate restTemplate = new RestTemplate();

    /* not used */
    @Operation(summary = "get current weather condition from certain location")
    @GetMapping(value = "/getWeather/{locationKey}", produces = "application/json")
    public String getWeather(@PathVariable String locationKey) {
        String url = "http://dataservice.accuweather.com/currentconditions/v1/"+locationKey+"?apikey="+API_KEY;
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return exchange.getBody();
    }

    @Operation(summary = "get current weather condition from certain location")
    @GetMapping(value = "/getWeatherByCity/{cityName}", produces = "application/json")
    public String getWeatherByCity(@PathVariable String cityName) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+API_KEY_OPEN+"&units=metric";
        HttpHeaders headers = new HttpHeaders();
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
            return exchange.getBody();
        } catch (HttpClientErrorException ex) {
            return ex.getResponseBodyAsString();
        }
    }

    /* not used */
    @Operation(summary = "get top cities weather condition")
    @GetMapping(value = "/topCitiesList", produces = "application/json")
    public String getTopCitiesList() {
        String url = "http://dataservice.accuweather.com/currentconditions/v1/topcities/150?apikey="+API_KEY;
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return exchange.getBody();
    }

    /* not used */
    @Operation(summary = "get list of cities from top cities")
    @GetMapping(value = "/cities", produces = "application/json")
    @ResponseBody
    public List<Map<String, String>> getCities() throws JsonProcessingException {
        List<Map<String, String>> result = new ArrayList<>();
        String topCitiesList = this.getTopCitiesList();
        ObjectMapper mapper = new ObjectMapper();
        List<LinkedHashMap<String,String>> response = mapper.readValue(topCitiesList, List.class);
        for (int i=0; i < response.size(); i++) {
            LinkedHashMap obj = response.get(i);
            if (obj.get("Key") != null && obj.get("EnglishName") != null) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("id", (String) obj.get("Key"));
                map.put("name", (String) obj.get("EnglishName"));
                result.add(map);
            }
        }
        return result;
    }

    /* not used */
    @Operation(summary = "get list of region")
    @GetMapping(value = "/regionList", produces = "application/json")
    public String getRegionList() {
        String url = "http://dataservice.accuweather.com/locations/v1/regions?apikey="+API_KEY;
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return exchange.getBody();
    }

    /* not used */
    @Operation(summary = "get list of country by its region code")
    @GetMapping( value = "/countryList", produces = "application/json")
    public String getCountryList() {
        String url = "http://dataservice.accuweather.com/locations/v1/countries/ASI?apikey="+API_KEY;
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        return exchange.getBody();
    }

}
