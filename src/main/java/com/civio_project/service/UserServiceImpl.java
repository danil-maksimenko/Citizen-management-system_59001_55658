package com.civio_project.service;

import com.civio_project.controller.request.RegistrationRequest;
import com.civio_project.entity.Address;
import com.civio_project.entity.Citizen;
import com.civio_project.entity.User;
import com.civio_project.repository.CitizenRepository;
import com.civio_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CitizenRepository citizenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.citizenRepository = citizenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Error: Username is already taken!");
        }
        Citizen citizen = new Citizen();
        citizen.setFirstName(request.getFirstName());
        citizen.setLastName(request.getLastName());
        citizen.setPassportNumber(request.getPassportNumber());
        citizen.setAddress(new Address(request.getStreet(), request.getCity(), request.getZipCode()));
        citizen.setUser(new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), "USER"));
        citizenRepository.save(citizen);
    }
}

