package com.asset.management.service;

import com.asset.management.model.AssetRegistration;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAssetRegistration {
//    AssetRegistration registerAsset(String name, Long companyId, String barcode,Long categoryId, String vendor, MultipartFile invoiceFile, double price, String status, String categoryType, String extraData1, String extraData2);

    AssetRegistration registerAsset(
            String name, Long companyId, Long categoryId, String vendor,
            MultipartFile invoiceFile, double price, String status,
            String categoryType,
            String serialNumber, String specifications, String brand, String type,
            List<String> licenses, String licenseExpiryDate, String version, String supportedOs
    );

}
