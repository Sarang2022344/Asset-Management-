package com.asset.management.dto;

import java.time.LocalDate;

public class AssetDTO {
    private String name;
    private double price;
    private String status;
    private String vendor;
    private String categoryType;
    private HardwareDTO hardwareDetails;
    private SoftwareDTO softwareDetails;

    public AssetDTO(String name, double price, String status, String vendor, String categoryType,
                    HardwareDTO hardwareDetails, SoftwareDTO softwareDetails) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.vendor = vendor;
        this.categoryType = categoryType;
        this.hardwareDetails = hardwareDetails;
        this.softwareDetails = softwareDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public HardwareDTO getHardwareDetails() {
        return hardwareDetails;
    }

    public void setHardwareDetails(HardwareDTO hardwareDetails) {
        this.hardwareDetails = hardwareDetails;
    }

    public SoftwareDTO getSoftwareDetails() {
        return softwareDetails;
    }

    public void setSoftwareDetails(SoftwareDTO softwareDetails) {
        this.softwareDetails = softwareDetails;
    }
}
