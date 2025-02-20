package com.asset.management.repository;

import com.asset.management.model.AssetAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetAllocationRepository extends JpaRepository<AssetAllocation, Long> {
    Optional<AssetAllocation> findByAsset_AssetId(Long assetId);
}
