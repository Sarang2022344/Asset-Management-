package com.asset.management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MaintenanceTicketDTO {

    private String description;
    private Long employeeId;

    public MaintenanceTicketDTO(){}

    public MaintenanceTicketDTO(String description, Long employeeId) {
        this.description = description;
        this.employeeId = employeeId;
    }


}
