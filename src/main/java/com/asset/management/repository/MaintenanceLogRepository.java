package com.asset.management.repository;

import com.asset.management.model.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog,Long> {
    List<MaintenanceLog> findByPerformedBy_UserId(Long userId);






    List<MaintenanceLog> findAll();

}
