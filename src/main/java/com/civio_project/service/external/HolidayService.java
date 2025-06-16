package com.civio_project.service.external;


import com.civio_project.controller.dto.PublicHolidayDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Year;

@Service
public class HolidayService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://date.nager.at/api/v3/PublicHolidays/";

    public PublicHolidayDTO[] getPublicHolidays(String countryCode) {
        int currentYear = Year.now().getValue();
        String url = API_URL + currentYear + "/" + countryCode;
        try {
            return restTemplate.getForObject(url, PublicHolidayDTO[].class);
        } catch (Exception e) {
            System.err.println("Error fetching holidays for " + countryCode + ": " + e.getMessage());
            return null;
        }
    }
}
