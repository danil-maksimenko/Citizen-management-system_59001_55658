package com.civio_project.service.external;

import com.civio_project.controller.dto.GeocodingDTO;
import com.civio_project.controller.dto.WeatherDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherDTO getWeatherForCity(String city) {
        GeocodingDTO.Result coordinates = getCoordinates(city);
        if (coordinates == null) {
            System.err.println("Could not find coordinates for city: " + city);
            return null;
        }
        return getWeather(coordinates.getLatitude(), coordinates.getLongitude());
    }

    private GeocodingDTO.Result getCoordinates(String city) {
        String url = String.format("https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&language=en&format=json", city);
        try {
            GeocodingDTO response = restTemplate.getForObject(url, GeocodingDTO.class);
            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                return response.getResults().get(0);
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error during geocoding request for city '" + city + "': " + e.getMessage());
            return null;
        }
    }

    private WeatherDTO getWeather(double latitude, double longitude) {
        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f&current_weather=true", latitude, longitude).replace(",", ".");
        try {
            return restTemplate.getForObject(url, WeatherDTO.class);
        } catch (Exception e) {
            System.err.println("Error during weather request: " + e.getMessage());
            return null;
        }
    }
}