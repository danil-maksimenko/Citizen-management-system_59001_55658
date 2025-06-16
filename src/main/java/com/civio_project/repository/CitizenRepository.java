package com.civio_project.repository;

import com.civio_project.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    Optional<Citizen> findByPassportNumber(String passportNumber);

    Optional<Citizen> findByUser_Username(String username);

}