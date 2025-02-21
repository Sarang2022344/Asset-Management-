package com.asset.management.controller;


import com.asset.management.dto.TicketDTO;
import com.asset.management.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("tickets")
@Tag(name = "Ticket Management", description = "APIs for managing employee tickets")

public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    //Raise a new maintenance ticket
    @Operation(summary = "Create a new ticket", description = "Allows an employee to raise a new ticket")
    @PostMapping(value ="/employee/{employeeId}",consumes = "multipart/form-data" )
    public ResponseEntity<TicketDTO> raiseTicket(
            @PathVariable Long employeeId,
            @RequestParam String issueDescription,
            @RequestParam(value = "issueImage", required = false) MultipartFile issueImageUrl){
        return ResponseEntity.ok(ticketService.raiseTicket(employeeId,issueDescription,issueImageUrl));
    }




    //Get all tickets raised by an employee
    @Operation(summary = "Get all tickets", description = "Retrieve a list of all raised tickets")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TicketDTO>> getTickets(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ticketService.getTicketsByEmployee(employeeId));
    }
    //Get details of a specific ticket
    @Operation(summary = "Get ticket by ticket id", description = "Retrieve ticket of all raised tickets")
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }
}
