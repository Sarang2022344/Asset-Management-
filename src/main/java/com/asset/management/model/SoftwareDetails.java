package com.asset.management.model;

import com.asset.management.service.CategorySpecificDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class SoftwareDetails implements CategorySpecificDetails {
    private List<String> licenses;
    private String licenseExpiryDate;
    private String Version;
    private String supportedos;

    public SoftwareDetails() {

    }

    public SoftwareDetails(List<String> licenses, String licenseExpiryDate, String version, String supportedos) {
        this.licenses = licenses;
        this.licenseExpiryDate = licenseExpiryDate;
        Version = version;
        this.supportedos = supportedos;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getSupportedos() {
        return supportedos;
    }

    public void setSupportedos(String supportedos) {
        this.supportedos = supportedos;
    }

    public String getVesion() {
        return Version;
    }

    public void setVesion(String vesion) {
        Version = vesion;
    }

    public List<String> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<String> licenses) {
        this.licenses = licenses;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    @Override
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting SoftwareDetails to JSON", e);
        }
    }



}
