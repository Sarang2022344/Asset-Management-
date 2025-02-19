package com.asset.management.service;

import com.asset.management.model.MaintenanceTicket;

public interface MaintenanceTicketService {

    String raisedTicket(Long employeeId, MaintenanceTicket ticket);
}
