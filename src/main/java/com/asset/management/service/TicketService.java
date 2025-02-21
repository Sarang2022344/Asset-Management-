package com.asset.management.service;

import com.asset.management.dto.TicketDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TicketService {
    TicketDTO raiseTicket(Long employeeId, String issueDescription, MultipartFile issueImageUrl);
    List<TicketDTO> getTicketsByEmployee(Long employeeId);
    TicketDTO getTicketById(Long ticketId);
    List<TicketDTO> getAllTickets();
}
