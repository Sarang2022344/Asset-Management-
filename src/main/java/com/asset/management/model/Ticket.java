package com.asset.management.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity

@Table(name="ticket_table")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee raisedBy;

//    @ManyToOne
//    @JoinColumn(name = "asset_id", nullable = true)
//    private AssetRegistration asset;


    private Long assetId;


    private String issueDescription;
    private String issueImageUrl;
    private LocalDate createdAt=LocalDate.now();
    private String status;        //"Pending", "Resolved"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(Employee raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueImageUrl() {
        return issueImageUrl;
    }

    public void setIssueImageUrl(String issueImageUrl) {
        this.issueImageUrl = issueImageUrl;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }


}


