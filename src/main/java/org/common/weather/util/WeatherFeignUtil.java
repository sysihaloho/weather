package org.common.weather.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherFeign", url = "https://api.openweathermap.org/data/2.5/weather")
public interface WeatherFeignUtil {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    String getCurrentWeather(@RequestParam(name = "q") String q,
                             @RequestParam(name = "appid") String appid,
                             @RequestParam(name = "units") String metrics);

}
