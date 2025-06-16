package com.civio_project.service;

import com.civio_project.entity.Citizen;
import com.civio_project.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository citizenRepository;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public List<Citizen> findAll() {
        return citizenRepository.findAll();
    }

    @Override
    public Citizen findByPassportNumber(String passportNumber) {
        return citizenRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new RuntimeException("Citizen not found with passport number: " + passportNumber));
    }

    @Override
    public Citizen findUserData(String username) {
        return citizenRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Citizen data not found for user: " + username));
    }

    @Override
    public Citizen save(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    @Override
    public Citizen updateByPassportNumber(String passportNumber, Citizen citizen) {
        Citizen existing = findByPassportNumber(passportNumber);
        existing.setFirstName(citizen.getFirstName());
        existing.setLastName(citizen.getLastName());
        existing.setPassportNumber(citizen.getPassportNumber());
        existing.setAddress(citizen.getAddress());
        return citizenRepository.save(existing);
    }

    @Override
    public void deleteByPassportNumber(String passportNumber) {
        Citizen existing = findByPassportNumber(passportNumber);
        citizenRepository.delete(existing);
    }
}