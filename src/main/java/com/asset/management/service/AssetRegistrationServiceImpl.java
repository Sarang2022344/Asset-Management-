package com.asset.management.service;

import com.asset.management.model.*;
import com.asset.management.repository.CategoryRepository;
import com.asset.management.repository.AssetRegistrationRepository;
import com.asset.management.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            List<String> licenses, String licenseExpiryDate, String version, String supportedOs) {

        Optional<Company> companyOpt = companyrepository.findById(companyId);
        Optional<Category> categoryOpt = categoryrepository.findById(categoryId);

        if (companyOpt.isEmpty() || categoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid Company or Category ID");
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
            String invoicePath = fileStorageService.saveFile(invoiceFile);
            asset.setInvoicePath(invoicePath);
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







}
