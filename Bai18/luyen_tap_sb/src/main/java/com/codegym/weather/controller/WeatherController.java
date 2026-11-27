package com.codegym.weather.controller;

import com.codegym.weather.model.Weather;
import com.codegym.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{username}/{password}/{location}")
    public ResponseEntity<Weather> getWeather(@PathVariable String username,
                                              @PathVariable String password,
                                              @PathVariable String location) {
        Weather weather = weatherService.getWeatherByLocation(location);
        return ResponseEntity.ok(weather);
    }
}
