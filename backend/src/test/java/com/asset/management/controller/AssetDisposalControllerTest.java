package com.asset.management.controller;

import com.asset.management.dto.AssetDisposalDTO;
import com.asset.management.service.AssetDisposalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

 class AssetDisposalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AssetDisposalService disposalService;

    @InjectMocks
    private AssetDisposalController assetDisposalController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetDisposalController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllDisposals() throws Exception {
        when(disposalService.getAllDisposals()).thenReturn(List.of(new AssetDisposalDTO()));

        mockMvc.perform(get("/disposal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetDisposalById() throws Exception {
        Long disposalId = 1L;
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO();
        when(disposalService.getDisposalById(disposalId)).thenReturn(disposalDTO);

        mockMvc.perform(get("/disposal/" + disposalId))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDisposedAssetsByDateRange() throws Exception {
        when(disposalService.getDisposedAssetsByDateRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(new AssetDisposalDTO()));

        mockMvc.perform(get("/disposal/range")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testDisposeAsset() throws Exception {
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO();
        when(disposalService.disposeAsset(any(AssetDisposalDTO.class))).thenReturn(disposalDTO);

        mockMvc.perform(post("/disposal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disposalDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateDisposal() throws Exception {
        Long disposalId = 1L;
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO();
        when(disposalService.updateDisposal(eq(disposalId), any(AssetDisposalDTO.class))).thenReturn(disposalDTO);

        mockMvc.perform(put("/disposal/" + disposalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disposalDTO)))
                .andExpect(status().isOk());
    }
}
