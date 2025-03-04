package com.asset.management.controller;

import com.asset.management.dto.MaintenanceLogDTO;
import com.asset.management.service.MaintenanceLogService;
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

public class MaintenanceLogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MaintenanceLogService maintenanceLogService;

    @InjectMocks
    private MaintenanceLogController maintenanceLogController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(maintenanceLogController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddLog() throws Exception {
        MockMultipartFile issueImage = new MockMultipartFile("issueImage", "issue.jpg", "image/jpeg", new byte[10]);

        MaintenanceLogDTO maintenanceLogDTO = new MaintenanceLogDTO();
        when(maintenanceLogService.addLog(anyLong(), anyString(), any())).thenReturn(maintenanceLogDTO);

        mockMvc.perform(multipart("/maintenanceLog/logs")
                        .file(issueImage)
                        .param("adminId", "1")
                        .param("issueDescription", "Broken keyboard"))
                .andExpect(status().isCreated());
    }



    @Test
    void testGetAllLogs() throws Exception {
        when(maintenanceLogService.getAllLogs()).thenReturn(List.of(new MaintenanceLogDTO()));

        mockMvc.perform(get("/maintenanceLog/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
