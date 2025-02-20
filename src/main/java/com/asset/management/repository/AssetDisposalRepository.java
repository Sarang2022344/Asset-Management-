package com.asset.management.repository;

import com.asset.management.model.AssetDisposal;
import com.asset.management.model.AssetRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetDisposalRepository extends JpaRepository<AssetDisposal, Long> {

}
