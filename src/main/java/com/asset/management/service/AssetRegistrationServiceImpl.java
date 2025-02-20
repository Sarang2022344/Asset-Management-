package com.asset.management.service;

import com.asset.management.dto.AssetDTO;
import com.asset.management.dto.HardwareDTO;
import com.asset.management.dto.SoftwareDTO;
import com.asset.management.exception.AssetNotFoundException;
import com.asset.management.exception.CSVProcessingException;
import com.asset.management.exception.InvalidAssetException;
import com.asset.management.model.*;
import com.asset.management.repository.CategoryRepository;
import com.asset.management.repository.AssetRegistrationRepository;
import com.asset.management.repository.CompanyRepository;
import com.asset.management.util.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.asset.management.util.CSVHelper;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssetRegistrationServiceImpl implements  IAssetRegistration {

private final AssetRegistrationRepository assetrepository;
private final CategoryRepository categoryrepository;
private final FileStorageService fileStorageService;
private final CompanyRepository companyrepository;

    public AssetRegistrationServiceImpl(AssetRegistrationRepository assetrepository, CategoryRepository categoryrepository, FileStorageService fileStorageService, CompanyRepository companyrepository) {
        this.assetrepository = assetrepository;
        this.categoryrepository = categoryrepository;
        this.fileStorageService = fileStorageService;
        this.companyrepository = companyrepository;
    }
    @Override
    public AssetRegistration registerAsset(
            String name, Long companyId, Long categoryId,
            String vendor, MultipartFile invoiceFile, double price,
            String status, String categoryType,
            String serialNumber, String specifications, String brand, String type,
            List<String> licenses, String licenseExpiryDate, String version, String supportedOs, List<MultipartFile> imageFiles) {

        Optional<Company> companyOpt = companyrepository.findById(companyId);
        Optional<Category> categoryOpt = categoryrepository.findById(categoryId);

        if (companyOpt.isEmpty() || categoryOpt.isEmpty()) {
            throw new InvalidAssetException("Invalid Company or Category ID");
        }


        AssetRegistration asset = new AssetRegistration();
        asset.setName(name);
        asset.setCompany(companyOpt.get());
        asset.setCategory(categoryOpt.get());
        asset.setVendor(vendor);
        asset.setPrice(price);
        asset.setStatus(status);

        String barcode = generateUniqueBarcode();
        asset.setBarcode(barcode);

        if (invoiceFile != null && !invoiceFile.isEmpty()) {
            String invoicePath = fileStorageService.saveFile(invoiceFile,"invoice");
            asset.setInvoicePath(invoicePath);
        }

        // Save image files if provided
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<String> imagePaths = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                String imagePath = fileStorageService.saveFile(imageFile, "image");
                imagePaths.add(imagePath);
            }
            asset.setImage(imagePaths);
        }

        if (categoryType.equalsIgnoreCase("Software")) {
            SoftwareDetails softwareDetails = new SoftwareDetails();
            softwareDetails.setAsset(asset);
            softwareDetails.setLicenses(licenses);
            softwareDetails.setLicenseExpiryDate(licenseExpiryDate);
            softwareDetails.setVersion(version);
            softwareDetails.setSupportedos(supportedOs);
            asset.setSoftwareDetails(softwareDetails);
        } else if (categoryType.equalsIgnoreCase("Hardware")) {
            HardwareDetails hardwareDetails = new HardwareDetails();
            hardwareDetails.setAsset(asset);
            hardwareDetails.setSerialNumber(serialNumber);
            hardwareDetails.setSpecifications(specifications);
            hardwareDetails.setBrand(brand);
            hardwareDetails.setType(type);
            asset.setHardwareDetails(hardwareDetails);
        }

        return assetrepository.save(asset);
    }

    private String generateUniqueBarcode() {
        return "ASSET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

public void saveAssetsFromCSV(MultipartFile file) {
    if (!CSVHelper.hasCSVFormat(file)) {
        throw new CSVProcessingException("Invalid CSV file format.");
    }
    try {
        List<AssetRegistration> assets = CSVHelper.csvToAssets(new InputStreamReader(file.getInputStream()));
        assetrepository.saveAll(assets);
    } catch (Exception e) {
        throw new CSVProcessingException("Failed to store CSV data", e);
    }
}




    @Override
    public void deleteAssetById(Long assetId) {
        Optional<AssetRegistration> asset = assetrepository.findById(assetId);
        if (asset.isEmpty()) {
            throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
        }
        assetrepository.deleteById(assetId);

    }

    @Override
    public AssetDTO getAssetById(Long assetId) {
        Optional<AssetRegistration> assetOpt = assetrepository.findById(assetId);

        if (assetOpt.isEmpty()) {
            throw new InvalidAssetException("Asset with ID " + assetId + " not found.");
        }

        AssetRegistration asset = assetOpt.get();
//        HardwareDetails hardwareDetails = asset.getHardwareDetails();
        SoftwareDetails softwareDetails = assetrepository.findById(assetId)
                .map(AssetRegistration::getSoftwareDetails)
                .orElse(null);

        HardwareDetails hardwareDetails = assetrepository.findById(assetId)
                .map(AssetRegistration::getHardwareDetails)
                .orElse(null);

        String categoryType = null;
        HardwareDTO hardwareDTO = null;
        SoftwareDTO softwareDTO = null;

        System.out.println( asset.getHardwareDetails());
        if (hardwareDetails != null) {
            categoryType = asset.getCategory().getCategoryName();
            hardwareDTO = new HardwareDTO(
                    hardwareDetails.getSerialNumber(),
                    hardwareDetails.getSpecifications(),
                    hardwareDetails.getBrand(),
                    hardwareDetails.getType()
            );
        }

        if (softwareDetails != null) {
            categoryType = asset.getCategory().getCategoryName();
            softwareDTO = new SoftwareDTO(
                    softwareDetails.getLicenses(),
                    softwareDetails.getLicenseExpiryDate(),
                    softwareDetails.getVersion(),
                    softwareDetails.getSupportedos()
            );
        }
        System.out.println("Returning asset: " + asset.getName() + " | Category: " + asset.getCategory().getCategoryName());

        System.out.println("Asset Found: " + asset.getName());
        System.out.println("Hardware Details: " + hardwareDTO);
        System.out.println("Software Details: " + softwareDTO);

        return new AssetDTO(
                asset.getName(),
                asset.getPrice(),
                asset.getStatus(),
                asset.getVendor(),
                categoryType,
                hardwareDTO,
                softwareDTO
        );
    }






}
