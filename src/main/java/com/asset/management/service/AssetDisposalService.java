package com.asset.management.service;

import com.asset.management.dto.AssetDisposalDTO;
import com.asset.management.dto.BulkAssetDisposalDTO;

import java.util.List;

public interface AssetDisposalService {
    AssetDisposalDTO disposeAsset(AssetDisposalDTO disposalDTO);
    AssetDisposalDTO updateDisposal(Long disposalId, AssetDisposalDTO disposalDTO);

    List<AssetDisposalDTO> getAllDisposals();
    AssetDisposalDTO getDisposalById(Long disposalId);
//    List<AssetDisposalDTO> disposeAssetsInBulk(BulkAssetDisposalDTO bulkDisposalDTO);
}
