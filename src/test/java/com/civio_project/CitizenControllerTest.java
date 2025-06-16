package com.civio_project;

import com.civio_project.controller.CitizenController;
import com.civio_project.entity.Citizen;
import com.civio_project.service.CitizenService;
import com.civio_project.service.CustomUserDetailsService;
import com.civio_project.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CitizenController.class)
@Import(CitizenControllerTest.TestConfig.class)
class CitizenControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CitizenService citizenService() {
            return Mockito.mock(CitizenService.class);
        }

        @Bean
        public JwtService jwtService() {
            return Mockito.mock(JwtService.class);
        }

        @Bean
        public CustomUserDetailsService userDetailsService() {
            return Mockito.mock(CustomUserDetailsService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private ObjectMapper objectMapper;

    private Citizen citizen;

    @BeforeEach
    void setUp() {
        citizen = new Citizen();
        citizen.setId(1L);
        citizen.setFirstName("John");
        citizen.setLastName("Doe");
        citizen.setPassportNumber("AB123456");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenGetAllCitizens_asAdmin_thenReturnJsonArray() throws Exception {
        given(citizenService.findAll()).willReturn(Collections.singletonList(citizen));

        mockMvc.perform(get("/api/citizens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void whenGetAllCitizens_asUser_thenOk() throws Exception {
        given(citizenService.findAll()).willReturn(Collections.singletonList(citizen));

        mockMvc.perform(get("/api/citizens"))
                .andExpect(status().isOk());
    }

}
