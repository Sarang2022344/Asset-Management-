package com.asset.management.controller;

import com.asset.management.model.AssetRegistration;
import com.asset.management.service.AssetRegistrationServiceImpl;
import com.asset.management.service.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class AssetRegisterController {
private final CategoryServiceImpl categoryService;
private final AssetRegistrationServiceImpl asssetRegistration;

    public AssetRegisterController(CategoryServiceImpl categoryService, AssetRegistrationServiceImpl asssetRegistration) {
        this.categoryService = categoryService;
        this.asssetRegistration = asssetRegistration;
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public AssetRegistration registerAsset(
            @RequestParam String name,
            @RequestParam Long companyId,
            @RequestParam Long categoryId,
            @RequestParam String vendor,
            @RequestParam(name="invoiceFile",required = false) MultipartFile invoiceFile,
            @RequestParam double price,
            @RequestParam String status,
            @RequestParam String categoryType,

            // Hardware fields
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) String specifications,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String type,

            // Software fields
            @RequestParam(required = false) List<String> licenses,
            @RequestParam(required = false) String licenseExpiryDate,
            @RequestParam(required = false) String version,
            @RequestParam(required = false) String supportedOs,
            // Image files
        @RequestParam(name="imageFiles", required = false) List<MultipartFile> imageFiles
    ) {
        return asssetRegistration.registerAsset(
                name, companyId, categoryId, vendor, invoiceFile, price, status, categoryType,
                serialNumber, specifications, brand, type,
                licenses, licenseExpiryDate, version, supportedOs, imageFiles
        );
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        asssetRegistration.saveAssetsFromCSV(file);
        return ResponseEntity.ok("CSV file uploaded and assets saved successfully!");
    }





    }







