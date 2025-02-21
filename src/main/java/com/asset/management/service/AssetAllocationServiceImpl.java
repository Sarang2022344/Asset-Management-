package com.asset.management.service;

import com.asset.management.exception.NoAssignedAssetsException;
import com.asset.management.model.AssetAllocation;
import com.asset.management.model.AssetRegistration;
import com.asset.management.model.Employee;
import com.asset.management.model.User;
import com.asset.management.repository.AssetAllocationRepository;


import com.asset.management.repository.AssetRegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AssetAllocationServiceImpl implements AssetAllocationService {

    private final AssetAllocationRepository allocationRepository;
    private final AssetRegistrationRepository assetRepository;


    public AssetAllocationServiceImpl(AssetAllocationRepository allocationRepository,
                                      AssetRegistrationRepository assetRepository) {
        this.allocationRepository = allocationRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public List<AssetAllocation> getAssignedAssets(Long employeeId) {
        // Query the repository for assets assigned to the given employee
        return allocationRepository.findByEmployee_EmployeeIdAndStatus(employeeId, "Assigned");
    }

    @Override
    public String updateAssetAllocation(Long allocationId, Map<String, Object> updates) {
        Optional<AssetAllocation> allocationOptional = allocationRepository.findById(allocationId);
        if (allocationOptional.isEmpty()) {
            return "Asset allocation record not found!";
        }

        AssetAllocation allocation = allocationOptional.get();

        // Update asset if provided
//        if (updates.containsKey("assetId")) {
//            Long assetId = Long.valueOf(updates.get("assetId").toString());
//            Optional<AssetRegistration> assetOptional = assetRepository.findById(assetId);
//            if (assetOptional.isEmpty()) {
//                return "Asset not found!";
//            }
//            allocation.setAsset(assetOptional.get());
//        }

        // Update employee if provided
        if (updates.containsKey("employeeId")) {
            Long employeeId = Long.valueOf(updates.get("employeeId").toString());
            Employee employee = new Employee();
            employee.setEmployeeId(employeeId);
            allocation.setEmployee(employee);
        }

        // Update status if provided
        if (updates.containsKey("status")) {
            allocation.setStatus(updates.get("status").toString());
        }

        // Update returnedDate if provided
        if (updates.containsKey("returnedDate")) {
            allocation.setReturnedDate(LocalDate.parse(updates.get("returnedDate").toString()));
        }

        // Update reason if provided
//        if (updates.containsKey("reason")) {
//            allocation.setReason(updates.get("reason").toString());
//        }

        // Save the updated allocation
        allocationRepository.save(allocation);
        return "Asset allocation successfully updated.";
    }


    public List<AssetAllocation> getAllAllocations() {
        return allocationRepository.findAll();
    }


    public List<AssetAllocation> getAllocationHistory(Long assetId) {
        return allocationRepository.findAllByAsset_AssetId(assetId);
    }


    public String returnAsset(Long allocationId, LocalDate returnedDate/*, String reason*/) {
        Optional<AssetAllocation> allocationOptional = allocationRepository.findById(allocationId);
        if (allocationOptional.isEmpty()) {
            return "Allocation record not found!";
        }

        AssetAllocation allocation = allocationOptional.get();
        if (!"Assigned".equals(allocation.getStatus())) {
            return "Asset is not currently assigned!";
        }

        if (returnedDate.isBefore(allocation.getAllocatedDate())) {
            return "Returned date cannot be before the allocated date!";
        }

        allocation.setStatus("Returned");
        allocation.setReturnedDate(returnedDate);
//        allocation.setReason(reason);
        allocationRepository.save(allocation);

        return "Asset successfully returned.";
    }

    @Override
    public List<AssetAllocation> getAllAssignedAssets() {
        List<AssetAllocation> assignedAssets = allocationRepository.findByStatus("Assigned");
        if (assignedAssets.isEmpty()) {
            throw new NoAssignedAssetsException("No assets are currently assigned.");
        }
        return assignedAssets;
    }

    @Override
    public String allocateAsset(Long assetId, Long employeeId, Long userId) {
        // Check if asset exists
        Optional<AssetRegistration> assetOptional = assetRepository.findById(assetId);
        if (assetOptional.isEmpty()) {
            return "Asset not found!";
        }

        // Check if asset is already allocated
        Optional<AssetAllocation> existingAllocation = allocationRepository.findByAsset_AssetId(assetId);
        if (existingAllocation.isPresent() && "Assigned".equals(existingAllocation.get().getStatus())) {
            return "Asset is already assigned!";
        }

        // Create an Employee object directly without fetching from the database
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        // Create new allocation
        AssetAllocation allocation = new AssetAllocation();
        allocation.setAsset(assetOptional.get());
        allocation.setEmployee(employee);
        allocation.setAllocatedDate(LocalDate.now());
        allocation.setStatus("Assigned");
        // Normally, you'd fetch the user from a UserRepository
        allocation.setPerformedBy(new User(userId.longValue(), "Admin")); // Example user

        allocationRepository.save(allocation);

        return "Asset successfully allocated!";
    }
}
