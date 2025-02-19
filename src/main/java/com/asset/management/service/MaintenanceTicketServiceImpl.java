package com.asset.management.service;

import com.asset.management.model.Employee;
import com.asset.management.model.MaintenanceTicket;
import com.asset.management.repository.EmployeeRepository;
import com.asset.management.repository.MaintenanceTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceTicketServiceImpl implements MaintenanceTicketService{

    @Autowired
    private MaintenanceTicketRepository maintenanceTicketRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public String raisedTicket(Long employeeId, MaintenanceTicket ticket) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));

        ticket.setRaisedBy(employee);
        ticket.setStatus("PENDING");
        maintenanceTicketRepository.save(ticket);
        return "Ticket raised successfully";



    }
}
