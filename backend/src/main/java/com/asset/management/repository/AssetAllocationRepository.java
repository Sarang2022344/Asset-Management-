package com.asset.management.repository;

import com.asset.management.model.AssetAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetAllocationRepository extends JpaRepository<AssetAllocation, Long> {
    Optional<AssetAllocation> findByAsset_AssetId(Long assetId);
    List<AssetAllocation> findAllByAsset_AssetId(Long assetId);
    List<AssetAllocation> findByStatus(String status);
    List<AssetAllocation> findByEmployee_EmployeeIdAndStatus(Long employeeId, String status);
    @Query("SELECT a FROM AssetAllocation a JOIN FETCH a.asset JOIN FETCH a.employee")
    List<AssetAllocation> findAllWithDetails();
//    Optional<AssetAllocation> findByEmployee_EmployeeIdAndAsset_Category(Long employeeId, String assetCategory);
}
