package com.asset.management.service;

import com.asset.management.dto.MaintenanceLogDTO;
import com.asset.management.model.MaintenanceLog;
import com.asset.management.model.User;
import com.asset.management.repository.MaintenanceLogRepository;
import com.asset.management.repository.UserRepository;
import com.asset.management.util.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaintenanceLogImpl implements MaintenanceLogService {
    private final MaintenanceLogRepository logRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;


    public MaintenanceLogImpl(MaintenanceLogRepository logRepository,
                              UserRepository userRepository,
                              FileStorageService fileStorageService) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;

    }
// can add maintenance log
    @Override
    public MaintenanceLogDTO addLog(Long adminId ,String issueDescription,MultipartFile issueImage) {
        User admin = userRepository.findById(adminId)
            .orElseThrow(() -> new RuntimeException("Admin user not found"));

        MaintenanceLog log = new MaintenanceLog();
        log.setIssueDescription(issueDescription);
        log.setStatus("Pending");
        log.setPerformedBy(admin);
        if (issueImage != null && !issueImage.isEmpty()) {
            String imageUrl = fileStorageService.saveFile(issueImage,"image");
            log.setIssueImageUrl(imageUrl);
        }
        MaintenanceLog savedLog = logRepository.save(log);
        return new MaintenanceLogDTO(savedLog);
}



    @Override
    public MaintenanceLogDTO updateStatus(Long logId, String status,MultipartFile invoiceFile) {
        MaintenanceLog log = logRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Log not found"));
        log.setStatus(status);

        if ("Resolved".equalsIgnoreCase(status) && invoiceFile != null && !invoiceFile.isEmpty()) {
            String invoicePath = fileStorageService.saveFile(invoiceFile, "invoice");
            log.setInvoicePath(invoicePath);
        }
        MaintenanceLog updatedLog = logRepository.save(log);
        return new MaintenanceLogDTO(updatedLog);
    }
    @Override
    public List<MaintenanceLogDTO> getAllLogs() {
        return logRepository.findAll()
                .stream().map(MaintenanceLogDTO::new)
                .collect(Collectors.toList());
    }


}
