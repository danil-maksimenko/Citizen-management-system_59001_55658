package com.civio_project.controller;

import com.civio_project.controller.dto.WeatherDTO;
import com.civio_project.entity.Citizen;
import com.civio_project.service.CitizenService;
import com.civio_project.service.external.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CitizenService citizenService;

    public WeatherController(WeatherService weatherService, CitizenService citizenService) {
        this.weatherService = weatherService;
        this.citizenService = citizenService;
    }

    @GetMapping("/my-city")
    public ResponseEntity<WeatherDTO> getWeatherForMyCity(Authentication authentication) {
        String username = authentication.getName();
        Citizen citizen = citizenService.findUserData(username);

        if (citizen == null || citizen.getAddress() == null || citizen.getAddress().getCity() == null) {
            return ResponseEntity.notFound().build();
        }

        String city = citizen.getAddress().getCity();
        WeatherDTO weather = weatherService.getWeatherForCity(city);

        return weather != null ? ResponseEntity.ok(weather) : ResponseEntity.notFound().build();
    }
}
