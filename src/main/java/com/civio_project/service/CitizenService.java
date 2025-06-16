package com.civio_project.service;

import com.civio_project.entity.Citizen;

import java.util.List;

public interface CitizenService {

    List<Citizen> findAll();

    Citizen findByPassportNumber(String passportNumber);

    Citizen findUserData(String username);

    Citizen save(Citizen citizen);

    Citizen updateByPassportNumber(String passportNumber, Citizen citizen);

    void deleteByPassportNumber(String passportNumber);
}
