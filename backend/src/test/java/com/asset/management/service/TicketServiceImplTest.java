package com.asset.management.service;

import com.asset.management.dto.TicketDTO;

import com.asset.management.model.AssetRegistration;
import com.asset.management.model.AssetAllocation;

import com.asset.management.model.Employee;
import com.asset.management.model.Ticket;
import com.asset.management.repository.EmployeeRepository;
import com.asset.management.repository.TicketRepository;
import com.asset.management.util.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AssetAllocationService assetAllocationService;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRaiseTicket_Success() {
        Long employeeId = 1L;
        String assetName = "Laptop";
        String issueDescription = "Screen not working";
        MultipartFile mockFile = mock(MultipartFile.class);

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(100L);
        asset.setName(assetName);

        AssetAllocation allocation = new AssetAllocation();
        allocation.setAsset(asset);
        allocation.setEmployee(employee);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(assetAllocationService.getAssignedAssets(employeeId)).thenReturn(List.of(allocation));
        when(fileStorageService.saveFile(mockFile, "image")).thenReturn("image-url");

        Ticket savedTicket = new Ticket();
        savedTicket.setAssetId(100L);
        savedTicket.setRaisedBy(employee);
        savedTicket.setIssueDescription(issueDescription);
        savedTicket.setIssueImageUrl("image-url");
        savedTicket.setStatus("Pending");

        when(ticketRepository.save(any())).thenReturn(savedTicket);

        TicketDTO result = ticketService.raiseTicket(employeeId, assetName, issueDescription, mockFile);

        assertNotNull(result);
        assertEquals("Pending", result.getStatus());
        verify(ticketRepository, times(1)).save(any());
    }

    @Test
    void testRaiseTicket_EmployeeNotFound() {
        Long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                ticketService.raiseTicket(employeeId, "Laptop", "Screen issue", null));

        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void testRaiseTicket_AssetNotAssigned() {
        Long employeeId = 1L;
        String assetName = "Laptop";

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(assetAllocationService.getAssignedAssets(employeeId)).thenReturn(List.of());

        Exception exception = assertThrows(RuntimeException.class, () ->
                ticketService.raiseTicket(employeeId, assetName, "Issue", null));

        assertEquals("Selected asset is not assigned to the employee", exception.getMessage());
    }

    @Test
    void testGetTicketById_Success() {
        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setIssueDescription("Screen issue");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        TicketDTO result = ticketService.getTicketById(ticketId);

        assertNotNull(result);
        assertEquals(ticketId, result.getTicketId());
    }

    @Test
    void testGetTicketById_NotFound() {
        Long ticketId = 1L;
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> ticketService.getTicketById(ticketId));

        assertEquals("Ticket not found", exception.getMessage());
    }

    @Test
    void testGetAllTickets() {
        when(ticketRepository.findAll()).thenReturn(List.of(new Ticket()));

        List<TicketDTO> result = ticketService.getAllTickets();

        assertFalse(result.isEmpty());
        verify(ticketRepository, times(1)).findAll();
    }
}
