package com.asset.management.model;

import com.asset.management.service.CategorySpecificDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

@Entity
@Table(name="harwadware_details")
public class HardwareDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@OneToOne
@JoinColumn(name="asset_id",nullable = false)
@JsonBackReference
private  AssetRegistration asset;
    private String serialNumber;
    private String specifications;
    private String brand;
    private String type;

    public String getSerialNumber() {
        return serialNumber;
    }

    public AssetRegistration getAsset() {
        return asset;
    }

    public void setAsset(AssetRegistration asset) {
        this.asset = asset;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HardwareDetails(Long id, AssetRegistration asset, String serialNumber, String specifications, String brand, String type) {
        this.id = id;
        this.asset = asset;
        this.serialNumber = serialNumber;
        this.specifications = specifications;
        this.brand = brand;
        this.type = type;
    }

    public HardwareDetails() {

    }






}
