package com.asset.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity


@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToMany(mappedBy = "employee")
    private List<AssetAllocation> assetAllocation;

    @Column(nullable = false)
    private String name;

    public Employee(String name) {
        this.name = name;
    }
}

