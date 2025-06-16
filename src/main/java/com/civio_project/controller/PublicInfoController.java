package com.civio_project.controller;

import com.civio_project.controller.dto.PublicHolidayDTO;
import com.civio_project.service.external.HolidayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicInfoController {

    private final HolidayService holidayService;

    public PublicInfoController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/holidays/{countryCode}")
    public ResponseEntity<PublicHolidayDTO[]> getPublicHolidays(@PathVariable String countryCode) {
        PublicHolidayDTO[] holidays = holidayService.getPublicHolidays(countryCode);
        return holidays != null ? ResponseEntity.ok(holidays) : ResponseEntity.notFound().build();
    }
}