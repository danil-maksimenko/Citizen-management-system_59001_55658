package com.civio_project.controller.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicHolidayDTO {
    private String date;
    private String localName;
    private String name;
    private String countryCode;
}