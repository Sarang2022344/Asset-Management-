package com.asset.management.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    // Get the project directory dynamically
    private static final String BASE_UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "invoices";

    public String saveFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(BASE_UPLOAD_DIR);

            // Ensure the directory exists
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Save the file
            file.transferTo(filePath.toFile());

            return filePath.toString(); // Return the saved file path
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
