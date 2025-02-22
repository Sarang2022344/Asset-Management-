package com.asset.management.dto;

public class HardwareDTO {
    private String serialNumber;
    private String specifications;
    private String brand;
    private String type;

    public HardwareDTO(String serialNumber, String specifications, String brand, String type) {
        this.serialNumber = serialNumber;
        this.specifications = specifications;
        this.brand = brand;
        this.type = type;
    }

    // Getters & Setters
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
