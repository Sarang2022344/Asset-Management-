package com.asset.management.util;

import com.asset.management.exception.FileStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceTest {

    private FileStorageService fileStorageService;

    @BeforeEach
    void setUp() {
        fileStorageService = new FileStorageService();
    }

    @Test
    void testSaveFileSuccessfully() throws IOException {
        // Mock a file
        MultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "dummy data".getBytes());

        // Call method
        String filePath = fileStorageService.saveFile(file, "image");

        // Assert file exists
        assertNotNull(filePath);
        assertTrue(Files.exists(Paths.get(filePath)));

        // Clean up: Delete test file
        new File(filePath).delete();
    }

    @Test
    void testSaveFileWithInvalidType() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "document.pdf", "application/pdf", "dummy data".getBytes());

        // Call method with an unhandled file type
        String filePath = fileStorageService.saveFile(file, "unknown");

        // Assert file is saved
        assertNotNull(filePath);
        assertTrue(Files.exists(Paths.get(filePath)));

        // Clean up
        new File(filePath).delete();
    }

    @Test
    void testSaveFileThrowsException() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("error.png");
        doThrow(new IOException("Disk error")).when(file).transferTo(any(File.class));

        // Verify that an exception is thrown
        assertThrows(FileStorageException.class, () -> fileStorageService.saveFile(file, "image"));
    }
}

