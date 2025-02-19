package com.asset.management.model;

import jakarta.persistence.*;

@Entity
@Table(name="asset")
public class AssetRegistration {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long assetId;

@Column(nullable = false)
    private String name ;

@ManyToOne
@JoinColumn(name="category_id")
private Category category;

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

    public AssetRegistration(long assetId, String name, Category category) {
        this.assetId = assetId;
        this.name = name;
        this.category = category;
    }

    public AssetRegistration() {

    }



}
