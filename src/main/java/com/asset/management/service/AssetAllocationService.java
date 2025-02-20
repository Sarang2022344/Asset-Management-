package com.asset.management.service;

import com.asset.management.model.AssetAllocation;

import java.time.LocalDate;
import java.util.List;

public interface AssetAllocationService {
    String allocateAsset(Long assetId, Long employeeId, Long userId);
    String returnAsset(Long allocationId, LocalDate returnedDate/*, String reason*/);
    List<AssetAllocation> getAllocationHistory(Long assetId);
    List<AssetAllocation> getAllAllocations();
    List<AssetAllocation> getAllAssignedAssets();

}

