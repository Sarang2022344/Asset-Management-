package com.asset.management.controller;

import com.asset.management.dto.TicketDTO;
import com.asset.management.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRaiseTicket() throws Exception {
        Long employeeId = 1L;
        MockMultipartFile issueImage = new MockMultipartFile("issueImage", "issue.jpg", "image/jpeg", new byte[10]);

        TicketDTO ticketDTO = new TicketDTO();
        when(ticketService.raiseTicket(anyLong(), anyString(), anyString(), any())).thenReturn(ticketDTO);

        mockMvc.perform(multipart("/tickets/employee/" + employeeId + "/raise-ticket")
                        .file(issueImage)
                        .param("assetName", "Laptop")
                        .param("issueDescription", "Screen not working"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTicketById() throws Exception {
        Long ticketId = 1L;
        TicketDTO ticketDTO = new TicketDTO();
        when(ticketService.getTicketById(ticketId)).thenReturn(ticketDTO);

        mockMvc.perform(get("/tickets/" + ticketId))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllTickets() throws Exception {
        when(ticketService.getAllTickets()).thenReturn(List.of(new TicketDTO()));

        mockMvc.perform(get("/tickets/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetTicketsByEmployee() throws Exception {
        Long employeeId = 1L;
        when(ticketService.getTicketsByEmployee(employeeId)).thenReturn(List.of(new TicketDTO()));

        mockMvc.perform(get("/tickets/employee/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
