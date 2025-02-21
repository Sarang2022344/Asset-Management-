package com.asset.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "maintenance_log")
public class MaintenanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String issueDescription;
    private String status;  // Possible values: Pending, In Progress, Completed

    @ManyToOne
    @JoinColumn(name = "performed_by", nullable = false)
    private User performedBy;  // Admin user_id who logged it

    private LocalDateTime createdAt = LocalDateTime.now();

    private String issueImageUrl;

    private String invoicePath;

    public MaintenanceLog(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getIssueImageUrl() {
        return issueImageUrl;
    }

    public void setIssueImageUrl(String issueImageUrl) {
        this.issueImageUrl = issueImageUrl;
    }

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }
}
