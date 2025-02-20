package com.asset.management.service;

import com.asset.management.dto.AssetDisposalDTO;
import com.asset.management.exception.AssetAlreadyDisposedException;
import com.asset.management.exception.ResourceNotFoundException;
import com.asset.management.model.AssetDisposal;
import com.asset.management.model.AssetRegistration;
import com.asset.management.model.Company;
import com.asset.management.repository.AssetDisposalRepository;
import com.asset.management.repository.AssetRegistrationRepository;
import com.asset.management.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // ✅ Enable Mockito for JUnit 5
class AssetDisposalServiceImplTest {

    @Mock
    private AssetDisposalRepository disposalRepository;

    @Mock
    private AssetRegistrationRepository assetRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private AssetDisposalServiceImpl disposalService;

    private AssetRegistration asset;
    private Company company;
    private AssetDisposal disposal;
    private AssetDisposalDTO disposalDTO;

    @BeforeEach
    void setUp() {
        // ✅ Mock Asset
        asset = new AssetRegistration();
        asset.setAssetId(1L);
        asset.setName("Laptop");
        asset.setStatus("Active");

        // ✅ Mock Company
        company = new Company();
        company.setCompanyId(1L);

        // ✅ Mock Disposal
        disposal = new AssetDisposal();
        disposal.setDisposalId(1L);
        disposal.setAsset(asset);
        disposal.setCompany(company);
        disposal.setDisposalDate(LocalDate.of(2025, 2, 20));
        disposal.setReason("End of Life");

        // ✅ Mock DTO
        disposalDTO = new AssetDisposalDTO(1L, 1L, 1L, LocalDate.of(2025, 2, 20), "End of Life");
    }

    @Test
    void testGetAllDisposals() {
        when(disposalRepository.findAll()).thenReturn(List.of(disposal));

        List<AssetDisposalDTO> result = disposalService.getAllDisposals();

        assertEquals(1, result.size());
        assertEquals(disposal.getDisposalId(), result.get(0).getDisposalId());
        verify(disposalRepository, times(1)).findAll();
    }

    @Test
    void testGetDisposalById_Success() {
        when(disposalRepository.findById(1L)).thenReturn(Optional.of(disposal));

        AssetDisposalDTO result = disposalService.getDisposalById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getDisposalId());
        assertEquals("End of Life", result.getReason());
        verify(disposalRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDisposalById_NotFound() {
        when(disposalRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> disposalService.getDisposalById(1L));
        assertEquals("Asset Disposal not found", exception.getMessage());
    }

    @Test
    void testDisposeAsset_Success() {
        // ✅ Mock Asset and Company found in DB
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // ✅ Ensure disposal object has an ID before returning from the mock
        disposal.setDisposalId(1L);  // **Manually setting the disposal ID**
        when(disposalRepository.save(any(AssetDisposal.class))).thenAnswer(invocation -> {
            AssetDisposal savedDisposal = invocation.getArgument(0);
            savedDisposal.setDisposalId(1L);  // **Ensure saved disposal has an ID**
            return savedDisposal;
        });

        // ✅ Call service method
        AssetDisposalDTO result = disposalService.disposeAsset(disposalDTO);

        // ✅ Assertions
        assertNotNull(result);
        assertEquals(1L, result.getDisposalId());  // Now this will not be null
        assertEquals("End of Life", result.getReason());

        // ✅ Verify interactions
        verify(assetRepository, times(1)).findById(1L);
        verify(companyRepository, times(1)).findById(1L);
        verify(assetRepository, times(1)).save(any(AssetRegistration.class));
        verify(disposalRepository, times(1)).save(any(AssetDisposal.class));
    }


    @Test
    void testDisposeAsset_AlreadyDisposed() {
        asset.setStatus("Disposed");

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));

        Exception exception = assertThrows(AssetAlreadyDisposedException.class, () -> disposalService.disposeAsset(disposalDTO));

        assertEquals("This asset has already been disposed and cannot be disposed again.", exception.getMessage());
        verify(disposalRepository, never()).save(any(AssetDisposal.class));
    }

    @Test
    void testDisposeAsset_AssetNotFound() {
        when(assetRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> disposalService.disposeAsset(disposalDTO));

        assertEquals("Asset not found with ID: 1", exception.getMessage());
        verify(disposalRepository, never()).save(any(AssetDisposal.class));
    }

    @Test
    void testDisposeAsset_CompanyNotFound() {
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> disposalService.disposeAsset(disposalDTO));

        assertEquals("Company not found with ID: 1", exception.getMessage());
        verify(disposalRepository, never()).save(any(AssetDisposal.class));
    }

    @Test
    void testUpdateDisposal_Success() {
        when(disposalRepository.findById(1L)).thenReturn(Optional.of(disposal));
        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(disposalRepository.save(any(AssetDisposal.class))).thenReturn(disposal);

        AssetDisposalDTO result = disposalService.updateDisposal(1L, disposalDTO);

        assertNotNull(result);
        assertEquals(1L, result.getDisposalId());
        assertEquals("End of Life", result.getReason());

        verify(disposalRepository, times(1)).save(any(AssetDisposal.class));
    }

    @Test
    void testUpdateDisposal_NotFound() {
        when(disposalRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> disposalService.updateDisposal(1L, disposalDTO));

        assertEquals("Asset Disposal record not found", exception.getMessage());
        verify(disposalRepository, never()).save(any(AssetDisposal.class));
    }
}
