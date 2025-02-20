package com.asset.management.controller;

import com.asset.management.model.AssetAllocation;
import com.asset.management.service.AssetAllocationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asset-allocation")
public class AssetAllocationController {

    private final AssetAllocationService assetAllocationService;

    public AssetAllocationController(AssetAllocationService assetAllocationService) {
        this.assetAllocationService = assetAllocationService;
    }

    @GetMapping("/all")
    public List<AssetAllocation> getAllAllocations() {
        return assetAllocationService.getAllAllocations();
    }


    @GetMapping("/history/{assetId}")
    public List<AssetAllocation> getAllocationHistory(@PathVariable Long assetId) {
        return assetAllocationService.getAllocationHistory(assetId);
    }


    @PostMapping("/return")
    public String returnAsset(@RequestBody Map<String, Object> request) {
        Long allocationId = Long.valueOf(request.get("allocationId").toString());
        LocalDate returnedDate = LocalDate.parse(request.get("returnedDate").toString());
//        String reason = (String) request.get("reason");

        return assetAllocationService.returnAsset(allocationId, returnedDate/*, reason*/);
    }

    @GetMapping("/assigned-assets")
    public List<AssetAllocation> getAllAssignedAssets() {
        try {
            return assetAllocationService.getAllAssignedAssets();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/assigned-assets/{employeeId}")
    public List<AssetAllocation> getAssignedAssets(@PathVariable Long employeeId) {
        return assetAllocationService.getAssignedAssets(employeeId);
    }


    @PutMapping("/update/{allocationId}")
    public String updateAssetAllocation(@PathVariable Long allocationId, @RequestBody Map<String, Object> updates) {
        return assetAllocationService.updateAssetAllocation(allocationId, updates);
    }

    @PostMapping("/allocate")
    public String allocateAsset(@RequestBody Map<String, Long> request) {
        Long assetId = request.get("assetId");
        Long employeeId = request.get("employeeId");
        Long userId = request.get("userId");

        if (assetId == null || employeeId == null || userId == null) {
            return "Missing required parameters!";
        }

        return assetAllocationService.allocateAsset(assetId, employeeId, userId);
    }
}
