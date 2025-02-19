package com.asset.management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    // Constructor
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category() {}

    // Getters only (No Setters to prevent modification)
    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
