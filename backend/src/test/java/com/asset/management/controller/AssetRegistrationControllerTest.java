package com.asset.management.controller;

import com.asset.management.dto.AssetDTO;
import com.asset.management.model.AssetRegistration;
import com.asset.management.service.AssetRegistrationServiceImpl;
import com.asset.management.service.CategoryServiceImpl;

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

 class AssetRegistrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private AssetRegistrationServiceImpl assetRegistrationService;

    @InjectMocks
    private AssetRegisterController assetRegisterController;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(assetRegisterController).build();

    }

    @Test
    void testRegisterAsset() throws Exception {
        MockMultipartFile invoiceFile = new MockMultipartFile("invoiceFile", "invoice.pdf", "application/pdf", new byte[10]);
        MockMultipartFile imageFile = new MockMultipartFile("imageFiles", "image.jpg", "image/jpeg", new byte[10]);

        when(assetRegistrationService.registerAsset(any(), anyLong(), anyLong(), any(), any(), anyDouble(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new AssetRegistration());

        mockMvc.perform(multipart("/api/registration/register")
                        .file(invoiceFile)
                        .file(imageFile)
                        .param("name", "Laptop")
                        .param("companyId", "1")
                        .param("categoryId", "1")
                        .param("vendor", "Dell")
                        .param("price", "1200.0")
                        .param("status", "Available")
                        .param("categoryType", "Hardware"))
                .andExpect(status().isOk());
    }

    @Test
    void testUploadCSV() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "assets.csv", "text/csv", "csv-data".getBytes());

        mockMvc.perform(multipart("/api/registration/uploadFile").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("CSV file uploaded and assets saved successfully!"));
    }

    @Test
    void testDeleteAsset() throws Exception {
        Long assetId = 1L;
        doNothing().when(assetRegistrationService).deleteAssetById(assetId);

        mockMvc.perform(delete("/api/registration/delete/" + assetId))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset with ID " + assetId + " deleted successfully."));
    }

    @Test
    void testGetAssetById() throws Exception {
        Long assetId = 1L;
        AssetDTO assetDTO = new AssetDTO("Laptop", 1200.0, "Available", "Dell", "Hardware", null, null);
        when(assetRegistrationService.getAssetById(assetId)).thenReturn(assetDTO);

        mockMvc.perform(get("/api/registration/get/" + assetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.vendor").value("Dell"))
                .andExpect(jsonPath("$.status").value("Available"))
                .andExpect(jsonPath("$.price").value(1200.0))
                .andExpect(jsonPath("$.categoryType").value("Hardware"));
    }


    @Test
    void testUpdateAsset() throws Exception {
        Long assetId = 1L;
        doNothing().when(assetRegistrationService).updateAsset(assetId, "Updated Laptop", "Updated Vendor", 1500.0, "Assigned");

        mockMvc.perform(put("/api/registration/update/" + assetId)
                        .param("name", "Updated Laptop")
                        .param("vendor", "Updated Vendor")
                        .param("price", "1500.0")
                        .param("status", "Assigned"))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset updated successfully."));
    }

    @Test
    void testGetAllAssets() throws Exception {
        when(assetRegistrationService.getAllAssets()).thenReturn(
                List.of(new AssetDTO("Laptop", 1200.0, "Available", "Dell", "Hardware", null, null)) // Matching constructor
        );

        mockMvc.perform(get("/api/registration/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].vendor").value("Dell"))
                .andExpect(jsonPath("$[0].status").value("Available"))
                .andExpect(jsonPath("$[0].price").value(1200.0))
                .andExpect(jsonPath("$[0].categoryType").value("Hardware"));
    }

}
