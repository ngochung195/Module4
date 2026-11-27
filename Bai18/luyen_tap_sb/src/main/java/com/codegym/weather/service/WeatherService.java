package com.codegym.weather.service;

import com.codegym.weather.exception.LocationNotFoundException;
import com.codegym.weather.model.Weather;
import com.codegym.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather getWeatherByLocation(String location) {
        return weatherRepository.findByLocationIgnoreCase(location)
                .orElseThrow(() -> new LocationNotFoundException(location));
    }
}
