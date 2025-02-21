package com.asset.management.controller;


import com.asset.management.dto.MaintenanceLogDTO;
import com.asset.management.service.MaintenanceLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("maintenanceLog")
public class MaintenanceLogController {
    private final MaintenanceLogService maintenanceLogService;

    public MaintenanceLogController(MaintenanceLogService maintenanceLogService) {
        this.maintenanceLogService = maintenanceLogService;
    }

    @PostMapping(value="/logs",consumes = "multipart/form-data")
    public ResponseEntity<MaintenanceLogDTO> addLog(
            @RequestParam Long adminId,
            @RequestParam String issueDescription,
            @RequestParam(value = "issueImage", required = false) MultipartFile issueImage) {
        MaintenanceLogDTO createdLog = maintenanceLogService.addLog(adminId,issueDescription, issueImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLog);
    }

    @PutMapping("/{logId}/resolve")
    public ResponseEntity<MaintenanceLogDTO> resolveLog(
            @PathVariable Long logId,
            @RequestParam(value = "invoiceFile", required = false) MultipartFile invoiceFile) {

        MaintenanceLogDTO updatedLog = maintenanceLogService.updateStatus(logId, "Resolved",invoiceFile);
        return ResponseEntity.ok(updatedLog);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MaintenanceLogDTO>> getAllLogs(){
        List<MaintenanceLogDTO> logs = maintenanceLogService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

}
