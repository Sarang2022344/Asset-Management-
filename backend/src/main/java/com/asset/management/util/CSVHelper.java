package com.asset.management.util;

import com.asset.management.exception.CSVProcessingException;
import com.asset.management.model.AssetRegistration;
import com.asset.management.model.HardwareDetails;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CSVHelper {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            return false;
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return false;
        }
        return TYPE.equals(file.getContentType()) ||
                (originalFilename.toLowerCase().endsWith(".csv"));
    }


    public static List<AssetRegistration> csvToAssets(InputStreamReader inputStreamReader) {
        if (inputStreamReader == null) {
            throw new CSVProcessingException("Input stream is null");
        }

        try (BufferedReader fileReader = new BufferedReader(inputStreamReader);
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.builder()
                             .setHeader()
                             .setIgnoreHeaderCase(true)
                             .setTrim(true)
                             .build())) {

            List<AssetRegistration> assets = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                AssetRegistration asset = new AssetRegistration();
                asset.setName(csvRecord.get("name"));
                asset.setVendor(csvRecord.get("vendor"));
                asset.setPrice(Double.parseDouble(csvRecord.get("price")));
                asset.setStatus(csvRecord.get("status"));

                asset.setBarcode("ASSET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

                asset.setPurchasedDate(LocalDate.parse(csvRecord.get("purchasedDate")));
                asset.setWarrantyStartDate(LocalDate.parse(csvRecord.get("warrantyStartDate")));
                asset.setWarrantyRenewalDate(LocalDate.parse(csvRecord.get("warrantyRenewalDate")));

                if (csvRecord.get("categoryType").equalsIgnoreCase("Hardware")) {
                    HardwareDetails hardwareDetails = new HardwareDetails();
                    hardwareDetails.setSerialNumber(csvRecord.get("serialNumber"));
                    hardwareDetails.setSpecifications(csvRecord.get("specifications"));
                    hardwareDetails.setBrand(csvRecord.get("brand"));
                    hardwareDetails.setType(csvRecord.get("type"));
                    asset.setHardwareDetails(hardwareDetails);
                }

                assets.add(asset);
            }

            return assets;

        } catch (IOException e) {
            throw new CSVProcessingException("Failed to parse CSV file: " + e.getMessage());
        }
    }

}
