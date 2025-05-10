package com.asset.management.controller;


import com.asset.management.model.AssetAllocation;
import com.asset.management.service.AssetAllocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 class AssetAllocationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AssetAllocationService assetAllocationService;

    @InjectMocks
    private AssetAllocationController assetAllocationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetAllocationController).build();
    }

    @Test
    void testGetAllAllocations() throws Exception {
        when(assetAllocationService.getAllAllocations()).thenReturn(List.of(new AssetAllocation()));

        mockMvc.perform(get("/api/asset-allocation/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetAllocationHistory() throws Exception {
        Long assetId = 1L;
        when(assetAllocationService.getAllocationHistory(assetId)).thenReturn(List.of(new AssetAllocation()));

        mockMvc.perform(get("/api/asset-allocation/history/" + assetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }



    @Test
    void testReturnAsset() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper(); // Convert Java objects to JSON

        Map<String, Object> request = Map.of(
                "allocationId", 1,
                "returnedDate", "2024-03-04"
        );

        when(assetAllocationService.returnAsset(anyLong(), any(LocalDate.class)))
                .thenReturn("Asset returned successfully");

        mockMvc.perform(post("/api/asset-allocation/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // Convert Map to JSON
                .andExpect(status().isOk())
                .andExpect(content().string("Asset returned successfully"));
    }

}