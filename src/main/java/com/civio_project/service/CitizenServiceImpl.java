package com.civio_project.service;

import com.civio_project.entity.Citizen;
import com.civio_project.repository.CitizenRepository;
import com.civio_project.controller.request.CitizenUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Citizen updateByPassportNumber(String passportNumber, CitizenUpdateRequestDTO citizenDetailsDTO) {
        Citizen existingCitizen = findByPassportNumber(passportNumber);
        existingCitizen.setFirstName(citizenDetailsDTO.getFirstName());
        existingCitizen.setLastName(citizenDetailsDTO.getLastName());
        if (citizenDetailsDTO.getAddress() != null) {
            if (existingCitizen.getAddress() == null) {
                existingCitizen.setAddress(new com.civio_project.entity.Address());
            }
            existingCitizen.getAddress().setStreet(citizenDetailsDTO.getAddress().getStreet());
            existingCitizen.getAddress().setCity(citizenDetailsDTO.getAddress().getCity());
            existingCitizen.getAddress().setZipCode(citizenDetailsDTO.getAddress().getZipCode());
        }
        return citizenRepository.save(existingCitizen);
    }

    @Override
    @Transactional
    public void deleteByPassportNumber(String passportNumber) {
        Citizen existing = findByPassportNumber(passportNumber);
        citizenRepository.delete(existing);
    }
}