package com.asset.management.repository;

import com.asset.management.model.AssetRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRegistrationRepository extends JpaRepository<AssetRegistration, Long> {
}
