package com.asset.management.controller;

import com.asset.management.dto.AssetDTO;
import com.asset.management.model.AssetRegistration;
import com.asset.management.service.AssetRegistrationServiceImpl;
import com.asset.management.service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AssetRegisterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private AssetRegistrationServiceImpl assetRegistrationService;

    @InjectMocks
    private AssetRegisterController assetRegisterController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(assetRegisterController).build();
    }

    @Test
    void testRegisterAsset_Success() throws Exception {
        AssetRegistration mockAsset = new AssetRegistration();
        mockAsset.setName("Test Asset");

        when(assetRegistrationService.registerAsset(any(),any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(mockAsset);

        mockMvc.perform(multipart("/api/registration/register")
                        .param("name", "Test Asset")
                        .param("companyId", "1")
                        .param("categoryId", "1")
                        .param("vendor", "Test Vendor")
                        .param("price", "100.0")
                        .param("status", "Available")
                        .param("categoryType", "Hardware"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Asset"));
    }

    @Test
    void testUploadCSV_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "CSV Data".getBytes());

        doNothing().when(assetRegistrationService).saveAssetsFromCSV(any());

        mockMvc.perform(multipart("/api/registration/uploadFile")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("CSV file uploaded and assets saved successfully!"));
    }

    @Test
    void testDeleteAsset_Success() throws Exception {
        doNothing().when(assetRegistrationService).deleteAssetById(1L);

        mockMvc.perform(delete("/api/registration/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset with ID 1 deleted successfully."));
    }

    @Test
    void testGetAssetById_Success() throws Exception {
        AssetDTO mockAssetDTO = new AssetDTO("Test Asset", 100.0, "Available", "Test Vendor", "Hardware", null, null);

        when(assetRegistrationService.getAssetById(1L)).thenReturn(mockAssetDTO);

        mockMvc.perform(get("/api/registration/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Asset"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.status").value("Available"))
                .andExpect(jsonPath("$.vendor").value("Test Vendor"));
    }
}
