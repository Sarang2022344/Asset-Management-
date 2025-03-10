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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AssetDisposalServiceImplTest {

    @Mock
    private AssetDisposalRepository disposalRepository;

    @Mock
    private AssetRegistrationRepository assetRepository;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private AssetDisposalServiceImpl disposalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDisposeAsset_Success() {
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO(null, 1L, 1L, LocalDate.now(), "Obsolete");
        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(1L);
        asset.setStatus("Available");

        Company company = new Company();
        company.setCompanyId(1L);

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(disposalRepository.save(any(AssetDisposal.class))).thenAnswer(invocation -> {
            AssetDisposal disposal = invocation.getArgument(0);
            disposal.setDisposalId(1L);
            disposal.setAsset(asset); // Ensure the asset is set
            disposal.setCompany(company); // Ensure the company is set
            return disposal;
        });

        AssetDisposalDTO result = disposalService.disposeAsset(disposalDTO);

        assertNotNull(result);
        assertEquals(1L, result.getDisposalId());
        assertEquals("Disposed", asset.getStatus());
        verify(assetRepository, times(1)).save(asset);
        verify(disposalRepository, times(1)).save(any(AssetDisposal.class));
    }

    @Test
    void testDisposeAsset_AssetNotFound() {
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO(null, 1L, 1L, LocalDate.now(), "Obsolete");

        when(assetRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> disposalService.disposeAsset(disposalDTO));
    }

    @Test
    void testDisposeAsset_AssetAlreadyDisposed() {
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO(null, 1L, 1L, LocalDate.now(), "Obsolete");
        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(1L);
        asset.setStatus("Disposed");

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));

        assertThrows(AssetAlreadyDisposedException.class, () -> disposalService.disposeAsset(disposalDTO));
    }

    @Test
    void testDisposeAsset_AssetAssigned() {
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO(null, 1L, 1L, LocalDate.now(), "Obsolete");
        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(1L);
        asset.setStatus("Assigned");

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));

        assertThrows(IllegalStateException.class, () -> disposalService.disposeAsset(disposalDTO));
    }

    @Test
    void testUpdateDisposal_Success() {
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO(1L, 1L, 1L, LocalDate.now(), "Obsolete");
        AssetDisposal disposal = new AssetDisposal();
        disposal.setDisposalId(1L);

        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(1L);
        disposal.setAsset(asset); // Ensure the asset is set

        Company company = new Company();
        company.setCompanyId(1L);
        disposal.setCompany(company); // Ensure the company is set

        when(disposalRepository.findById(1L)).thenReturn(Optional.of(disposal));
        when(disposalRepository.save(any(AssetDisposal.class))).thenReturn(disposal);

        AssetDisposalDTO result = disposalService.updateDisposal(1L, disposalDTO);

        assertNotNull(result);
        assertEquals(1L, result.getDisposalId());
        verify(disposalRepository, times(1)).save(disposal);
    }

    @Test
    void testUpdateDisposal_DisposalNotFound() {
        AssetDisposalDTO disposalDTO = new AssetDisposalDTO(1L, 1L, 1L, LocalDate.now(), "Obsolete");

        when(disposalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> disposalService.updateDisposal(1L, disposalDTO));
    }

    @Test
    void testGetAllDisposals() {
        AssetDisposal disposal = new AssetDisposal();
        disposal.setDisposalId(1L);

        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(1L);
        disposal.setAsset(asset); // Ensure the asset is set

        Company company = new Company();
        company.setCompanyId(1L);
        disposal.setCompany(company); // Ensure the company is set

        when(disposalRepository.findAll()).thenReturn(Collections.singletonList(disposal));

        List<AssetDisposalDTO> result = disposalService.getAllDisposals();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getDisposalId());
    }

    @Test
    void testGetDisposalById_Success() {
        AssetDisposal disposal = new AssetDisposal();
        disposal.setDisposalId(1L);

        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(1L);
        disposal.setAsset(asset); // Ensure the asset is set

        Company company = new Company();
        company.setCompanyId(1L);
        disposal.setCompany(company); // Ensure the company is set

        when(disposalRepository.findById(1L)).thenReturn(Optional.of(disposal));

        AssetDisposalDTO result = disposalService.getDisposalById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getDisposalId());
    }

    @Test
    void testGetDisposalById_NotFound() {
        when(disposalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> disposalService.getDisposalById(1L));
    }

    @Test
    void testGetDisposedAssetsByDateRange() {
        AssetDisposal disposal = new AssetDisposal();
        disposal.setDisposalId(1L);
        disposal.setDisposalDate(LocalDate.now());

        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(1L);
        disposal.setAsset(asset); // Ensure the asset is set

        Company company = new Company();
        company.setCompanyId(1L);
        disposal.setCompany(company); // Ensure the company is set

        when(disposalRepository.findByDisposalDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.singletonList(disposal));

        List<AssetDisposalDTO> result = disposalService.getDisposedAssetsByDateRange(LocalDate.now().minusDays(1), LocalDate.now());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getDisposalId());
    }
}