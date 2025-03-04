package com.asset.management.service;

import com.asset.management.exception.NoAssignedAssetsException;
import com.asset.management.model.AssetAllocation;
import com.asset.management.model.AssetRegistration;
import com.asset.management.repository.AssetAllocationRepository;
import com.asset.management.repository.AssetRegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetAllocationServiceImplTest {

    @Mock
    private AssetAllocationRepository allocationRepository;

    @Mock
    private AssetRegistrationRepository assetRepository;

    @InjectMocks
    private AssetAllocationServiceImpl assetAllocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAssignedAssets() {
        Long employeeId = 1L;
        when(allocationRepository.findByEmployee_EmployeeIdAndStatus(employeeId, "Assigned"))
                .thenReturn(List.of(new AssetAllocation()));

        List<AssetAllocation> result = assetAllocationService.getAssignedAssets(employeeId);

        assertFalse(result.isEmpty());
        verify(allocationRepository, times(1)).findByEmployee_EmployeeIdAndStatus(employeeId, "Assigned");
    }

    @Test
    void testUpdateAssetAllocation_NotFound() {
        Long allocationId = 1L;
        when(allocationRepository.findById(allocationId)).thenReturn(Optional.empty());

        String result = assetAllocationService.updateAssetAllocation(allocationId, Map.of("status", "Returned"));

        assertEquals("Asset allocation record not found!", result);
    }

    @Test
    void testReturnAsset_Success() {
        Long allocationId = 1L;
        LocalDate returnedDate = LocalDate.now();
        AssetAllocation allocation = new AssetAllocation();
        allocation.setAllocatedDate(returnedDate.minusDays(1));
        allocation.setStatus("Assigned");

        when(allocationRepository.findById(allocationId)).thenReturn(Optional.of(allocation));

        String result = assetAllocationService.returnAsset(allocationId, returnedDate);

        assertEquals("Asset successfully returned.", result);
        assertEquals("Returned", allocation.getStatus());
        assertEquals(returnedDate, allocation.getReturnedDate());
        verify(allocationRepository, times(1)).save(allocation);
    }

    @Test
    void testReturnAsset_AlreadyReturned() {
        Long allocationId = 1L;
        AssetAllocation allocation = new AssetAllocation();
        allocation.setStatus("Returned");

        when(allocationRepository.findById(allocationId)).thenReturn(Optional.of(allocation));

        String result = assetAllocationService.returnAsset(allocationId, LocalDate.now());

        assertEquals("Asset is not currently assigned!", result);
    }

    @Test
    void testAllocateAsset_Success() {
        Long assetId = 1L, employeeId = 2L, userId = 3L;
        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(assetId);

        when(assetRepository.findById(assetId)).thenReturn(Optional.of(asset));
        when(allocationRepository.findByAsset_AssetId(assetId)).thenReturn(Optional.empty());

        String result = assetAllocationService.allocateAsset(assetId, employeeId, userId);

        assertEquals("Asset successfully allocated!", result);
        verify(allocationRepository, times(1)).save(any(AssetAllocation.class));
    }

    @Test
    void testGetAllAssignedAssets_NoAssets() {
        when(allocationRepository.findByStatus("Assigned")).thenReturn(List.of());

        assertThrows(NoAssignedAssetsException.class, () -> assetAllocationService.getAllAssignedAssets());
    }
}
