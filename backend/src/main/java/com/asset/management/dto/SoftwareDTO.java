package com.asset.management.dto;

import java.util.List;

public class SoftwareDTO {
    private List<String> licenses;
    private String licenseExpiryDate;
    private String version;
    private String supportedOs;

    public SoftwareDTO(List<String> licenses, String licenseExpiryDate, String version, String supportedOs) {
        this.licenses = licenses;
        this.licenseExpiryDate = licenseExpiryDate;
        this.version = version;
        this.supportedOs = supportedOs;
    }

    // Getters & Setters
    public List<String> getLicenses() { return licenses; }
    public void setLicenses(List<String> licenses) { this.licenses = licenses; }

    public String getLicenseExpiryDate() { return licenseExpiryDate; }
    public void setLicenseExpiryDate(String licenseExpiryDate) { this.licenseExpiryDate = licenseExpiryDate; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getSupportedOs() { return supportedOs; }
    public void setSupportedOs(String supportedOs) { this.supportedOs = supportedOs; }
}
