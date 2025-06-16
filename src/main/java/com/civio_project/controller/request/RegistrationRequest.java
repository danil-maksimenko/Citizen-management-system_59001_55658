package com.civio_project.controller.request;

import lombok.Data;

@Data
public class RegistrationRequest {

        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String passportNumber;
        private String street;
        private String city;
        private String zipCode;
}
