package com.asset.management.dto;

import com.asset.management.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
public class TicketDTO {
    private Long ticketId;
    private String issueDescription;
    private String issueImageUrl;
    private LocalDate createdAt;
    private String status;
//    private AssetRegistrationDTO asset;

    public TicketDTO(Ticket ticket) {
        this.ticketId = ticket.getId();
        this.issueDescription = ticket.getIssueDescription();
        this.issueImageUrl = ticket.getIssueImageUrl();
        this.status = ticket.getStatus();
        this.createdAt = ticket.getCreatedAt();


    }
    public TicketDTO(){

    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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
}
