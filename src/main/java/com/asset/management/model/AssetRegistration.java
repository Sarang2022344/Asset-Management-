package com.asset.management.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="asset")
public class AssetRegistration {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long assetId;

@ManyToOne
@JoinColumn(name="company_id",nullable=false)
private Company company;

@Column(nullable = false)
    private String name ;

@ManyToOne
@JoinColumn(name="category_id")
private Category category;

@ElementCollection
private List<String> image;

@Column(unique = true)
private String barcode ;


private LocalDate purchasedDate;
private String vendor;
private String invoicePath;
private Double price;
private String status;
    private LocalDate warrantyStartDate;
    private LocalDate warrantyRenewalDate;
    @Column(columnDefinition = "TEXT")
    private String additionalDetails;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(LocalDate warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public LocalDate getWarrantyRenewalDate() {
        return warrantyRenewalDate;
    }

    public void setWarrantyRenewalDate(LocalDate warrantyRenewalDate) {
        this.warrantyRenewalDate = warrantyRenewalDate;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AssetRegistration(long assetId, Company company, String name, Category category, List<String> image, String barcode, LocalDate purchasedDate, String vendor, String invoicePath, Double price, String status, LocalDate warrantyStartDate, LocalDate warrantyRenewalDate, String additionalDetails) {
        this.assetId = assetId;
        this.company = company;
        this.name = name;
        this.category = category;
        this.image = image;
        this.barcode = barcode;
        this.purchasedDate = purchasedDate;
        this.vendor = vendor;
        this.invoicePath = invoicePath;
        this.price = price;
        this.status = status;
        this.warrantyStartDate = warrantyStartDate;
        this.warrantyRenewalDate = warrantyRenewalDate;
        this.additionalDetails = additionalDetails;
    }

    public AssetRegistration() {

    }



}
