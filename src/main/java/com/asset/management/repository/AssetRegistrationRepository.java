package com.asset.management.repository;

import com.asset.management.model.AssetDisposal;
import com.asset.management.model.AssetRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRegistrationRepository extends JpaRepository<AssetRegistration, Long> {
//    List<AssetRegistration> findByAssetIdIn(List<Long> assetIds);
//List<AssetDisposal> findByAsset_AssetIdIn(List<Long> assetIds);

}
