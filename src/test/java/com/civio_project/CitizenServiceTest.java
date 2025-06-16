package com.civio_project;

import com.civio_project.entity.Address;
import com.civio_project.entity.Citizen;
import com.civio_project.repository.CitizenRepository;
import com.civio_project.service.CitizenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CitizenServiceTest {

    @Mock
    private CitizenRepository citizenRepository;

    @InjectMocks
    private CitizenServiceImpl citizenService;

    private Citizen citizen;

    @BeforeEach
    void setUp() {
        Address address = new Address("123 Main St", "Springfield", "12345");
        citizen = new Citizen();
        citizen.setId(1L);
        citizen.setFirstName("John");
        citizen.setLastName("Doe");
        citizen.setPassportNumber("AB123456");
        citizen.setAddress(address);
    }

    @Test
    void whenFindAll_thenReturnCitizenList() {
        given(citizenRepository.findAll()).willReturn(Collections.singletonList(citizen));
        List<Citizen> result = citizenService.findAll();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void whenFindByValidPassportNumber_thenReturnCitizen() {
        when(citizenRepository.findByPassportNumber("AB123456")).thenReturn(Optional.of(citizen));
        Citizen found = citizenService.findByPassportNumber("AB123456");
        assertThat(found).isNotNull();
    }

    @Test
    void whenFindByInvalidPassportNumber_thenThrowException() {
        when(citizenRepository.findByPassportNumber("XX999999")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            citizenService.findByPassportNumber("XX999999");
        });
    }
}
