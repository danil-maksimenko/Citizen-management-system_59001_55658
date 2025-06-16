package com.civio_project.controller;

import com.civio_project.entity.Citizen;
import com.civio_project.controller.request.CitizenUpdateRequestDTO;
import com.civio_project.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    private final CitizenService citizenService;

    @Autowired
    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping
    public List<Citizen> getAllCitizens() {
        return citizenService.findAll();
    }

    @GetMapping("/passport/{passportNumber}")
    public ResponseEntity<Citizen> getCitizenByPassportNumber(@PathVariable String passportNumber) {
        Citizen citizen = citizenService.findByPassportNumber(passportNumber);
        return ResponseEntity.ok(citizen);
    }

    @GetMapping("/username")
    public ResponseEntity<Citizen> getMyData(Authentication authentication) {
        String username = authentication.getName();
        Citizen citizen = citizenService.findUserData(username);
        return ResponseEntity.ok(citizen);
    }

    @PutMapping("/passport/{passportNumber}")
    public ResponseEntity<Citizen> updateCitizen(@PathVariable String passportNumber, @RequestBody CitizenUpdateRequestDTO citizenDetailsDTO) {

        Citizen updatedCitizen = citizenService.updateByPassportNumber(passportNumber, citizenDetailsDTO);
        return ResponseEntity.ok(updatedCitizen);
    }

    @DeleteMapping("/passport/{passportNumber}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable String passportNumber) {
        citizenService.deleteByPassportNumber(passportNumber);
        return ResponseEntity.noContent().build();
    }
}