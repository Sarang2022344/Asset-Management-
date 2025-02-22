package com.asset.management.controller;

import com.asset.management.dto.AssetDisposalDTO;
import com.asset.management.service.AssetDisposalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)  // ✅ Use Mockito extension
class AssetDisposalControllerTest {

    private MockMvc mockMvc;

    @Mock  // ✅ Manually mock the service instead of using @MockBean
    private AssetDisposalService disposalService;

    @InjectMocks  // ✅ Inject mock service into the controller
    private AssetDisposalController disposalController;

    private ObjectMapper objectMapper;
    private AssetDisposalDTO disposalDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(disposalController).build();  // ✅ Manual MockMvc setup

        // ✅ Fix for LocalDate Serialization Issue
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Enable Java 8 Date/Time support

        disposalDTO = new AssetDisposalDTO(1L, 1L, 1L, LocalDate.of(2025, 2, 20), "End of life cycle");
    }

    @Test
    void testGetAllDisposals() throws Exception {
        List<AssetDisposalDTO> disposals = List.of(disposalDTO);
        when(disposalService.getAllDisposals()).thenReturn(disposals);

        mockMvc.perform(get("/disposal"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(disposals.size()))
                .andExpect(jsonPath("$[0].disposalId").value(disposalDTO.getDisposalId()));
    }

    @Test
    void testGetDisposalById() throws Exception {
        when(disposalService.getDisposalById(1L)).thenReturn(disposalDTO);

        mockMvc.perform(get("/disposal/{disposalId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.disposalId").value(1L))
                .andExpect(jsonPath("$.reason").value("End of life cycle"));
    }

    @Test
    void testDisposeAsset() throws Exception {
        when(disposalService.disposeAsset(any(AssetDisposalDTO.class))).thenReturn(disposalDTO);

        mockMvc.perform(post("/disposal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disposalDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.disposalId").value(1L))
                .andExpect(jsonPath("$.reason").value("End of life cycle"));
    }

    @Test
    void testUpdateDisposal() throws Exception {
        AssetDisposalDTO updatedDTO = new AssetDisposalDTO(1L, 1L, 1L, LocalDate.of(2025, 2, 25), "Updated reason");
        when(disposalService.updateDisposal(eq(1L), any(AssetDisposalDTO.class))).thenReturn(updatedDTO);

        mockMvc.perform(put("/disposal/{disposalId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Updated reason"));
    }
}
