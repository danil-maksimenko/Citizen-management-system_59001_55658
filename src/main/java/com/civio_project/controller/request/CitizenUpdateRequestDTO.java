package com.civio_project.controller.request;

import com.civio_project.entity.Address;
import lombok.Data;

@Data
public class CitizenUpdateRequestDTO {
    private String firstName;
    private String lastName;
    private Address address;
}
