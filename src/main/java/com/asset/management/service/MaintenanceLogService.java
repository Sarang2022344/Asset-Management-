package com.asset.management.service;

import com.asset.management.dto.MaintenanceLogDTO;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface MaintenanceLogService {
    MaintenanceLogDTO addLog(Long adminId,String issueDescription,MultipartFile issueImageUrl);
    List<MaintenanceLogDTO> getAllLogs();
    MaintenanceLogDTO updateStatus(Long logId, String status,MultipartFile invoiceFile);
}
