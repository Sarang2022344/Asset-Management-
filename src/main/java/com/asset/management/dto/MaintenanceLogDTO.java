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
    private Long technicianId;
    private TicketDTO ticket;
    private String issueImageUrl;
    private String invoicePath;

    public MaintenanceLogDTO(MaintenanceLog log) {
        this.id = log.getId();
        this.issueDescription = log.getIssueDescription();
        this.status = log.getStatus();
        this.performedBy = log.getPerformedBy().getUserId();
        this.createdAt = log.getCreatedAt();
//        this.invoicePath = log.getInvoicePath();
//        if(log.getTicket() != null) {
//            this.ticket = new TicketDTO(log.getTicket());
//        }

        this.issueImageUrl = log.getIssueImageUrl();

//        this.technicianId = (log.getAssignedTechnician() != null) ? log.getAssignedTechnician().getId() : null;
    }
    public MaintenanceLogDTO(){}


}
