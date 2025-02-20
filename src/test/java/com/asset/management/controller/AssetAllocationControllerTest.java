package com.asset.management.controller;

import com.asset.management.service.AssetAllocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AssetAllocationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AssetAllocationService assetAllocationService;

    @InjectMocks
    private AssetAllocationController assetAllocationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(assetAllocationController).build();
    }

    @Test
    void getAllAllocations_ShouldReturnList() throws Exception {
        when(assetAllocationService.getAllAllocations()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/asset-allocation/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void returnAsset_ShouldReturnSuccessMessage() throws Exception {
        when(assetAllocationService.returnAsset(anyLong(), any(LocalDate.class)))
                .thenReturn("Asset successfully returned.");

        mockMvc.perform(post("/api/asset-allocation/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"allocationId\": 1, \"returnedDate\": \"2025-02-20\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset successfully returned."));
    }
}
