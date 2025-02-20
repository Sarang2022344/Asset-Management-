package com.asset.management.service;

import com.asset.management.model.AssetAllocation;
import com.asset.management.model.AssetRegistration;
import com.asset.management.model.Employee;
import com.asset.management.model.User;
import com.asset.management.repository.AssetAllocationRepository;


import com.asset.management.repository.AssetRegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        allocation.setPerformedBy(new User(userId.intValue(), "Admin")); // Example user

        allocationRepository.save(allocation);

        return "Asset successfully allocated!";
    }
}
