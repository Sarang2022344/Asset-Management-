package com.asset.management.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="maintenance_request")
public class MaintenanceTicket {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String description;

    private String status ="PENDING";// default status when ticket raise

    @ManyToOne
    @JoinColumn(name="employeeId",nullable = false)

    private Employee raisedBy;  //only employees can raise the ticket

    private LocalDateTime createdAt=LocalDateTime.now();



}
