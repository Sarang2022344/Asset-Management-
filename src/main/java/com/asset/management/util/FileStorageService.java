//package com.asset.management.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//@Service
//public class FileStorageService {
//
//    private static final String BASE_UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "invoices";
//
//    public String saveFile(MultipartFile file) {
//        try {
//            Path uploadPath = Paths.get(BASE_UPLOAD_DIR);
//
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//            Path filePath = uploadPath.resolve(fileName);
//
//            file.transferTo(filePath.toFile());
//
//            return filePath.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store file", e);
//        }
//    }
//}
//
//
//
//



package com.asset.management.util;

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

    // Base upload directory
    private static final String BASE_UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads";

    public String saveFile(MultipartFile file, String fileType) {
        try {
            // Set upload directory based on file type
            String subFolder = fileType.equalsIgnoreCase("image") ? "images" : "invoices";
            Path uploadPath = Paths.get(BASE_UPLOAD_DIR, subFolder);

            // Ensure directory exists
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Save file to directory
            file.transferTo(filePath.toFile());

            return filePath.toString(); // Return saved file path
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
