package com.asset.management.controller;


import com.asset.management.dto.AssetDisposalDTO;
import com.asset.management.service.AssetDisposalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disposal")
public class AssetDisposalController {
    private final AssetDisposalService disposalService;

    public AssetDisposalController(AssetDisposalService disposalService) {
        this.disposalService = disposalService;
    }

    @PostMapping
    public ResponseEntity<AssetDisposalDTO> disposeAsset(@RequestBody AssetDisposalDTO disposalDTO) {
        AssetDisposalDTO responseDTO = disposalService.disposeAsset(disposalDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{disposalId}")
    public ResponseEntity<AssetDisposalDTO> updateDisposal(@PathVariable Long disposalId,
                                                           @RequestBody AssetDisposalDTO disposalDTO) {
        AssetDisposalDTO updatedDisposal = disposalService.updateDisposal(disposalId, disposalDTO);
        return ResponseEntity.ok(updatedDisposal);
    }

}
