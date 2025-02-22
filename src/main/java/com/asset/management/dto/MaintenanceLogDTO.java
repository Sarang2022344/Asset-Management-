package com.asset.management.dto;

import com.asset.management.model.MaintenanceLog;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Data
public class MaintenanceLogDTO {
    private Long id;
    private String issueDescription;
    private String status;
    private Long performedBy; // Admin user ID
    private LocalDateTime createdAt;
    private String issueImageUrl;
    private String invoicePath;

    public MaintenanceLogDTO(MaintenanceLog log) {
        this.id = log.getId();
        this.issueDescription = log.getIssueDescription();
        this.status = log.getStatus();
        this.performedBy = log.getPerformedBy().getUserId();
        this.createdAt = log.getCreatedAt();
        this.invoicePath = log.getInvoicePath();
        this.issueImageUrl = log.getIssueImageUrl();

    }
    public MaintenanceLogDTO(){}

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }
}
