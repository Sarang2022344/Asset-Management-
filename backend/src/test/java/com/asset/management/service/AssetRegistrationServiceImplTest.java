package com.asset.management.service;

import com.asset.management.dto.AssetDTO;
import com.asset.management.exception.AssetNotFoundException;
import com.asset.management.exception.CSVProcessingException;
import com.asset.management.exception.InvalidAssetException;
import com.asset.management.model.AssetRegistration;
import com.asset.management.model.Category;
import com.asset.management.model.Company;
import com.asset.management.repository.AssetRegistrationRepository;
import com.asset.management.repository.CategoryRepository;
import com.asset.management.repository.CompanyRepository;
import com.asset.management.util.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetRegistrationServiceImplTest {

    @Mock
    private AssetRegistrationRepository assetRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private AssetRegistrationServiceImpl assetRegistrationService;

    private AssetRegistration testAsset;
    private Company testCompany;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCompany = new Company();
        testCategory = new Category();

        testAsset = new AssetRegistration();
        testAsset.setName("Test Asset");
        testAsset.setCompany(testCompany);
        testAsset.setCategory(testCategory);
        testAsset.setVendor("Test Vendor");
        testAsset.setPrice(100.0);
        testAsset.setStatus("Available");
        testAsset.setBarcode("ASSET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }

    @Test
    void testRegisterAsset_Success() {
        when(companyRepository.findById(1L)).thenReturn(Optional.of(testCompany));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
        when(assetRepository.save(any())).thenReturn(testAsset);

        AssetRegistration result = assetRegistrationService.registerAsset(
                "Test Asset", 1L, 1L, "Test Vendor", null, 100.0, "Available",
                "Hardware", null, null, null, null, null, null, null, null, null
        );

        assertNotNull(result);
        assertEquals("Test Asset", result.getName());
        assertEquals("Test Vendor", result.getVendor());
        verify(assetRepository, times(1)).save(any());
    }

    @Test
    void testRegisterAsset_Failure_InvalidCompanyOrCategory() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidAssetException.class, () -> {
            assetRegistrationService.registerAsset(
                    "Test Asset", 1L, 1L, "Test Vendor", null, 100.0, "Available",
                    "Hardware", null, null, null, null, null, null, null, null, null
            );
        });

        assertEquals("Invalid Company or Category ID", exception.getMessage());
    }

    @Test
    void testDeleteAssetById_Success() {
        when(assetRepository.findById(1L)).thenReturn(Optional.of(testAsset));
        doNothing().when(assetRepository).deleteById(1L);

        assertDoesNotThrow(() -> assetRegistrationService.deleteAssetById(1L));

        verify(assetRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAssetById_Failure_NotFound() {
        when(assetRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AssetNotFoundException.class, () -> {
            assetRegistrationService.deleteAssetById(1L);
        });

        assertEquals("Asset with ID 1 not found.", exception.getMessage());
    }

    @Test
    void testSaveAssetsFromCSV_Success() {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "name,vendor,price\nTest,Vendor,100".getBytes());

        assertDoesNotThrow(() -> assetRegistrationService.saveAssetsFromCSV(file));
    }

    @Test
    void testSaveAssetsFromCSV_Failure_InvalidFormat() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Invalid data".getBytes());

        Exception exception = assertThrows(CSVProcessingException.class, () -> {
            assetRegistrationService.saveAssetsFromCSV(file);
        });

        assertEquals("Invalid CSV file format.", exception.getMessage());
    }

    @Test
    void testGetAssetById_Success() {
        when(assetRepository.findById(1L)).thenReturn(Optional.of(testAsset));

        AssetDTO result = assetRegistrationService.getAssetById(1L);

        assertNotNull(result);
        assertEquals("Test Asset", result.getName());
    }

    @Test
    void testGetAssetById_Failure_NotFound() {
        when(assetRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidAssetException.class, () -> {
            assetRegistrationService.getAssetById(1L);
        });

        assertEquals("Asset with ID 1 not found.", exception.getMessage());
    }
}
