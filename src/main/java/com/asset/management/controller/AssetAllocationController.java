package com.asset.management.controller;

import com.asset.management.service.AssetAllocationService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/asset-allocation")
public class AssetAllocationController {

    private final AssetAllocationService assetAllocationService;

    public AssetAllocationController(AssetAllocationService assetAllocationService) {
        this.assetAllocationService = assetAllocationService;
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
