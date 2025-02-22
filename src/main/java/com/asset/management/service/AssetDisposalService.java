package com.asset.management.service;

import com.asset.management.dto.AssetDisposalDTO;

import java.time.LocalDate;
import java.util.List;

public interface AssetDisposalService {
    AssetDisposalDTO disposeAsset(AssetDisposalDTO disposalDTO);
    AssetDisposalDTO updateDisposal(Long disposalId, AssetDisposalDTO disposalDTO);

    List<AssetDisposalDTO> getAllDisposals();
    AssetDisposalDTO getDisposalById(Long disposalId);
    List<AssetDisposalDTO> getDisposedAssetsByDateRange(LocalDate startDate, LocalDate endDate);

}
