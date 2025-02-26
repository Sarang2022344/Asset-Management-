package com.asset.management.controller;

import com.asset.management.dto.TicketDTO;
import com.asset.management.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private TicketDTO sampleTicket1;
    private TicketDTO sampleTicket2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        sampleTicket1 = new TicketDTO();
        sampleTicket1.setTicketId(1L);
        sampleTicket1.setIssueDescription("Issue with asset 1");
        sampleTicket1.setIssueImageUrl("http://example.com/image1.jpg");
        sampleTicket1.setStatus("Pending");
        sampleTicket1.setCreatedAt(LocalDate.now());
        sampleTicket1.setAssetId(101L);

        sampleTicket2 = new TicketDTO();
        sampleTicket2.setTicketId(2L);
        sampleTicket2.setIssueDescription("Issue with asset 2");
        sampleTicket2.setIssueImageUrl("http://example.com/image2.jpg");
        sampleTicket2.setStatus("Pending");
        sampleTicket2.setCreatedAt(LocalDate.now());
        sampleTicket2.setAssetId(102L);
    }

    @Test
    void testRaiseTicket_ShouldReturnTicket() throws Exception {
        Long employeeId = 1L;
        String assetName = "Laptop";
        String issueDescription = "Screen flickering";

        // Create a dummy file for issueImage
        MockMultipartFile imageFile = new MockMultipartFile("issueImage", "test.jpg",
                MediaType.IMAGE_JPEG_VALUE, "dummy image content".getBytes());

        // Mock the service call
        when(ticketService.raiseTicket(eq(employeeId), eq(assetName), eq(issueDescription), any()))
                .thenReturn(sampleTicket1);

        // Perform multipart POST request
        mockMvc.perform(multipart("/tickets/employee/{employeeId}/raise-ticket", employeeId)
                        .file(imageFile)
                        .param("assetName", assetName)
                        .param("issueDescription", issueDescription)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId", is(sampleTicket1.getTicketId().intValue())))
                .andExpect(jsonPath("$.issueDescription", is(sampleTicket1.getIssueDescription())))
                .andExpect(jsonPath("$.status", is(sampleTicket1.getStatus())));
    }

    @Test
    void testGetTicketsByEmployee_ShouldReturnTicketList() throws Exception {
        Long employeeId = 1L;
        List<TicketDTO> tickets = Arrays.asList(sampleTicket1, sampleTicket2);
        when(ticketService.getTicketsByEmployee(employeeId)).thenReturn(tickets);

        mockMvc.perform(get("/tickets/employee/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ticketId", is(sampleTicket1.getTicketId().intValue())))
                .andExpect(jsonPath("$[1].ticketId", is(sampleTicket2.getTicketId().intValue())));
    }

    @Test
    void testGetTicketById_ShouldReturnTicket() throws Exception {
        Long ticketId = 1L;
        when(ticketService.getTicketById(ticketId)).thenReturn(sampleTicket1);

        mockMvc.perform(get("/tickets/{ticketId}", ticketId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId", is(sampleTicket1.getTicketId().intValue())))
                .andExpect(jsonPath("$.issueDescription", is(sampleTicket1.getIssueDescription())));
    }

    @Test
    void testGetAllTickets_ShouldReturnAllTickets() throws Exception {
        List<TicketDTO> tickets = Arrays.asList(sampleTicket1, sampleTicket2);
        when(ticketService.getAllTickets()).thenReturn(tickets);

        mockMvc.perform(get("/tickets/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ticketId", is(sampleTicket1.getTicketId().intValue())))
                .andExpect(jsonPath("$[1].ticketId", is(sampleTicket2.getTicketId().intValue())));
    }
}
