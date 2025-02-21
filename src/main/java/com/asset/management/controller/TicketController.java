package com.asset.management.controller;


import com.asset.management.dto.TicketDTO;
import com.asset.management.service.TicketService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("tickets")

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    //Raise a new maintenance ticket

    @PostMapping(value = "/employee/{employeeId}/raise-ticket", consumes = "multipart/form-data")
    public ResponseEntity<TicketDTO> raiseTicket(
            @PathVariable Long employeeId,
            @RequestParam String assetName,  // Employee selects asset name
            @RequestParam String issueDescription,
            @RequestParam(value = "issueImage", required = false) MultipartFile issueImageUrl) {
        return ResponseEntity.ok(ticketService.raiseTicket(employeeId, assetName, issueDescription, issueImageUrl));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TicketDTO>> getTickets(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ticketService.getTicketsByEmployee(employeeId));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

}
