package com.asset.management.service;
import com.asset.management.dto.TicketDTO;
import com.asset.management.model.AssetAllocation;
import com.asset.management.model.AssetRegistration;
import com.asset.management.model.Employee;
import com.asset.management.model.Ticket;
import com.asset.management.repository.TicketRepository;
import com.asset.management.repository.EmployeeRepository;
import com.asset.management.util.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TicketServiceImplTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AssetAllocationService assetAllocationService;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private TicketServiceImpl ticketServiceImpl;

    // Sample test data
    private Employee sampleEmployee;
    private AssetRegistration sampleAsset;
    private AssetAllocation sampleAssetAllocation;
    private Ticket sampleTicket;

    @BeforeEach
    public void setUp() {
        // Create a sample Employee
        sampleEmployee = new Employee();
        sampleEmployee.setEmployeeId(1L);
        sampleEmployee.setName("John Doe");

        // Create a sample AssetRegistration with asset name "Laptop"
        sampleAsset = new AssetRegistration();
        sampleAsset.setAssetId(101L);
        sampleAsset.setName("Laptop");

        // Create a sample AssetAllocation with status "Assigned"
        sampleAssetAllocation = new AssetAllocation();
        sampleAssetAllocation.setAsset(sampleAsset);
        sampleAssetAllocation.setStatus("Assigned");

        // For simplicity, assume the employee has one allocated asset:
        sampleEmployee.setAssetAllocation(Collections.singletonList(sampleAssetAllocation));

        // Create a sample Ticket
        sampleTicket = new Ticket();
        sampleTicket.setId(1L);
        sampleTicket.setRaisedBy(sampleEmployee);
        sampleTicket.setIssueDescription("Screen flickering");
        sampleTicket.setStatus("Pending");
        // Here we mimic storing assetId in ticket (if your Ticket entity uses a primitive field)
        sampleTicket.setAssetId(sampleAsset.getAssetId());
    }

    @Test
    public void testRaiseTicket_Success() throws Exception {
        Long employeeId = 1L;
        String assetName = "Laptop";
        String issueDescription = "Screen flickering";

        // Create a dummy MultipartFile for the issue image
        MultipartFile dummyFile = mock(MultipartFile.class);
        when(dummyFile.isEmpty()).thenReturn(false);
        when(dummyFile.getOriginalFilename()).thenReturn("test.jpg");
        when(fileStorageService.saveFile(dummyFile, "image")).thenReturn("http://example.com/test.jpg");

        // Mock the EmployeeRepository to return sampleEmployee
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(sampleEmployee));

        // Mock the AssetAllocationService to return a list with the sample asset allocation
        when(assetAllocationService.getAssignedAssets(employeeId))
                .thenReturn(Collections.singletonList(sampleAssetAllocation));

        // Mock the TicketRepository save method to return a ticket with an ID set
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        TicketDTO result = ticketServiceImpl.raiseTicket(employeeId, assetName, issueDescription, dummyFile);

        assertNotNull(result);
        assertEquals(1L, result.getTicketId());
        assertEquals(issueDescription, result.getIssueDescription());
        assertEquals("Pending", result.getStatus());
        // Verify that assetId in the ticket matches the sample asset's assetId
        assertEquals(sampleAsset.getAssetId(), result.getAssetId());
        // Verify that the issue image URL is set as expected
        assertEquals("http://example.com/test.jpg", result.getIssueImageUrl());
    }

    @Test
    public void testGetTicketById_Success() {
        Long ticketId = 1L;
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(sampleTicket));

        TicketDTO result = ticketServiceImpl.getTicketById(ticketId);
        assertNotNull(result);
        assertEquals(ticketId, result.getTicketId());
        assertEquals("Screen flickering", result.getIssueDescription());
    }

    @Test
    public void testGetTicketsByEmployee_Success() {
        Long employeeId = 1L;
        when(ticketRepository.findByRaisedBy_EmployeeId(employeeId)).thenReturn(Arrays.asList(sampleTicket));

        List<TicketDTO> result = ticketServiceImpl.getTicketsByEmployee(employeeId);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Screen flickering", result.get(0).getIssueDescription());
    }

    @Test
    public void testGetAllTickets_Success() {
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(sampleTicket));

        List<TicketDTO> result = ticketServiceImpl.getAllTickets();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
