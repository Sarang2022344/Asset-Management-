package com.asset.management.repository;

import com.asset.management.model.AssetDisposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetDisposalRepository extends JpaRepository<AssetDisposal, Long> {
}
