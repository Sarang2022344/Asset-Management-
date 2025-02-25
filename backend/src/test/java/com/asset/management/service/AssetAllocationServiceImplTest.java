package com.asset.management.service;

import com.asset.management.model.AssetAllocation;
import com.asset.management.repository.AssetAllocationRepository;
import com.asset.management.repository.AssetRegistrationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetAllocationServiceImplTest {

    @Mock
    private AssetAllocationRepository allocationRepository;

    @Mock
    private AssetRegistrationRepository assetRepository;

    @InjectMocks
    private AssetAllocationServiceImpl assetAllocationService;

    @Test
    void getAllAllocations_ShouldReturnEmptyList() {
        when(allocationRepository.findAll()).thenReturn(Collections.emptyList());
        List<AssetAllocation> allocations = assetAllocationService.getAllAllocations();
        assertTrue(allocations.isEmpty());
    }

    @Test
    void returnAsset_ShouldReturnSuccessMessage() {
        Long allocationId = 1L;
        LocalDate returnDate = LocalDate.now();
        AssetAllocation allocation = new AssetAllocation();
        allocation.setAllocatedDate(returnDate.minusDays(1));
        allocation.setStatus("Assigned");

        when(allocationRepository.findById(allocationId)).thenReturn(Optional.of(allocation));
        String result = assetAllocationService.returnAsset(allocationId, returnDate);
        assertEquals("Asset successfully returned.", result);
        verify(allocationRepository).save(any(AssetAllocation.class));
    }
}
