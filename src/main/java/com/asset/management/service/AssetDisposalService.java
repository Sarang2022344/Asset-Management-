package com.asset.management.service;

import com.asset.management.dto.AssetDisposalDTO;

public interface AssetDisposalService {
    AssetDisposalDTO disposeAsset(AssetDisposalDTO disposalDTO);
    AssetDisposalDTO updateDisposal(Long disposalId, AssetDisposalDTO disposalDTO);
}
