package com.asset.management.controller;

import com.asset.management.model.MaintenanceTicket;
import com.asset.management.repository.EmployeeRepository;
import com.asset.management.repository.MaintenanceTicketRepository;
import com.asset.management.service.MaintenanceTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class MaintenanceTicketController {

    @Autowired
    private MaintenanceTicketService ticketService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/raised/{employeeId}")
    public ResponseEntity<String> raisedTicket(@PathVariable Long employeeId, @RequestBody MaintenanceTicket ticket){
        return ResponseEntity.ok(ticketService.raisedTicket(employeeId,ticket));
    }



}
