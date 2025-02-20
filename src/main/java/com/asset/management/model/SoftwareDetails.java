package com.asset.management.model;

import com.asset.management.service.CategorySpecificDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name="software_details")
public class SoftwareDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="asset_id",nullable=false)
    @JsonBackReference
    private AssetRegistration asset;

    @ElementCollection
    private List<String> licenses;
    private String licenseExpiryDate;
    private String Version;
    private String supportedos;

    public SoftwareDetails() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetRegistration getAsset() {
        return asset;
    }

    public void setAsset(AssetRegistration asset) {
        this.asset = asset;
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

    public SoftwareDetails(Long id, AssetRegistration asset, List<String> licenses, String licenseExpiryDate, String version, String supportedos) {
        this.id = id;
        this.asset = asset;
        this.licenses = licenses;
        this.licenseExpiryDate = licenseExpiryDate;
        Version = version;
        this.supportedos = supportedos;
    }
}
