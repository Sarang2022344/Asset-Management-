package com.asset.management.service;

import com.asset.management.dto.MaintenanceLogDTO;
import com.asset.management.model.MaintenanceLog;
import com.asset.management.model.User;
import com.asset.management.repository.MaintenanceLogRepository;
import com.asset.management.repository.UserRepository;
import com.asset.management.util.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaintenanceLogImplTest {

    @Mock
    private MaintenanceLogRepository logRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private MaintenanceLogImpl maintenanceLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLog_Success() {
        Long adminId = 1L;
        User admin = new User();
        admin.setUserId(adminId);

        MaintenanceLog savedLog = new MaintenanceLog();
        savedLog.setIssueDescription("Printer issue");
        savedLog.setStatus("Pending");
        savedLog.setPerformedBy(admin);

        when(userRepository.findById(adminId)).thenReturn(Optional.of(admin));
        when(logRepository.save(any())).thenReturn(savedLog);

        MaintenanceLogDTO result = maintenanceLogService.addLog(adminId, "Printer issue", null);

        assertNotNull(result);
        assertEquals("Pending", result.getStatus());
        verify(logRepository, times(1)).save(any());
    }

    @Test
    void testAddLog_AdminNotFound() {
        Long adminId = 1L;
        when(userRepository.findById(adminId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> maintenanceLogService.addLog(adminId, "Printer issue", null));

        assertEquals("Admin user not found", exception.getMessage());
    }




    @Test
    void testUpdateStatus_LogNotFound() {
        Long logId = 1L;
        when(logRepository.findById(logId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> maintenanceLogService.updateStatus(logId, "Resolved", null));

        assertEquals("Log not found", exception.getMessage());
    }


}
