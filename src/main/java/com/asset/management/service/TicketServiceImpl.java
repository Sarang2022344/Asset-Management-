package com.asset.management.service;

import com.asset.management.dto.AssetDTO;
import com.asset.management.dto.TicketDTO;

import com.asset.management.exception.NoAssignedAssetsException;

import com.asset.management.model.AssetAllocation;
import com.asset.management.model.Employee;
import com.asset.management.model.Ticket;
import com.asset.management.repository.AssetAllocationRepository;
import com.asset.management.repository.EmployeeRepository;
import com.asset.management.repository.TicketRepository;

import com.asset.management.util.FileStorageService;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService{
    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;
//    private final AssetAllocationRepository assetAllocationRepository;
private final AssetAllocationService assetAllocationService;
    private final FileStorageService fileStorageService;


    public TicketServiceImpl(TicketRepository ticketRepository, EmployeeRepository employeeRepository, AssetAllocationRepository assetAllocationRepository, AssetAllocationService assetAllocationService, FileStorageService fileStorageService) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
        this.assetAllocationService = assetAllocationService;
        //this.assetAllocationRepository = assetAllocationRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public TicketDTO raiseTicket(Long employeeId, String assetName, String issueDescription, MultipartFile issueImageUrl) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Fetch assigned assets using AssetAllocationService
        List<AssetAllocation> assignedAssets = assetAllocationService.getAssignedAssets(employeeId);

        // Find assetId based on assetName
        AssetAllocation selectedAssetAllocation = assignedAssets.stream()
                .filter(assetAllocation -> assetAllocation.getAsset().getName().equalsIgnoreCase(assetName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Selected asset is not assigned to the employee"));

        Long assetId = selectedAssetAllocation.getAsset().getAssetId(); // Extract assetId

        // Create the ticket
        Ticket ticket = new Ticket();
        ticket.setAssetId(assetId);
        ticket.setRaisedBy(employee);
        ticket.setIssueDescription(issueDescription);
        ticket.setStatus("Pending");

        if (issueImageUrl != null && !issueImageUrl.isEmpty()) {
            String imageUrl = fileStorageService.saveFile(issueImageUrl, "image");
            ticket.setIssueImageUrl(imageUrl);
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        return new TicketDTO(savedTicket);
    }


    //    @Override
//    public TicketDTO raiseTicket(Long employeeId,String issueDescription, MultipartFile issueImageUrl) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        // Log the issue description to check its value
//        System.out.println("Issue Description received: " + issueDescription);
//
//        Ticket ticket = new Ticket();
//       for(AssetAllocation assetAllocated :employee.getAssetAllocation()){
//           if(assetAllocated.getStatus().equalsIgnoreCase("Assigned")){
//               ticket.setAssetId(assetAllocated.getAsset().getAssetId());
//           }
//       }
//
//
//
//        ticket.setRaisedBy(employee);
//        ticket.setIssueDescription(issueDescription);
//        ticket.setStatus("Pending");
//
//
//        // Save issue image and store the URL
//        if (issueImageUrl != null && !issueImageUrl.isEmpty()) {
//            String imageUrl = fileStorageService.saveFile(issueImageUrl,"image");
//            ticket.setIssueImageUrl(imageUrl);
//            System.out.println("Issue Image URL set to: " + imageUrl);
//        }
//
//        Ticket savedTicket = ticketRepository.save(ticket);
//        return new TicketDTO(savedTicket);
//    }
//get to know which tickets are raised
    @Override
    public List<TicketDTO> getTicketsByEmployee(Long employeeId) {
        return ticketRepository.findByRaisedBy_EmployeeId(employeeId)
                .stream().map(TicketDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    public TicketDTO getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return new TicketDTO(ticket);
    }

    public List<TicketDTO> getAllTickets(){
        return ticketRepository.findAll()
                .stream().map(TicketDTO::new)
                .collect(Collectors.toList());

    }

}
