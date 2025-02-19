package com.asset.management.model;

import com.asset.management.service.CategorySpecificDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HardwareDetails implements CategorySpecificDetails {

    private String serialNumber;
    private String specifications;
    private String brand;
    private String type;


    public String getSerialNumber() {
        return serialNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public HardwareDetails(String serialNumber, String specifications, String brand, String type) {
        this.serialNumber = serialNumber;
        this.specifications = specifications;
        this.brand = brand;
        this.type = type;
    }

    public HardwareDetails() {

    }


    @Override
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting HardwareDetails to JSON", e);
        }
    }




}
