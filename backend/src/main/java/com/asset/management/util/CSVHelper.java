package com.asset.management.util;

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
        return TYPE.equals(file.getContentType()) || file.getOriginalFilename().endsWith(".csv");
    }

    public static List<AssetRegistration> csvToAssets(InputStreamReader inputStreamReader) {
        try (BufferedReader fileReader = new BufferedReader(inputStreamReader);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim())) {

            List<AssetRegistration> assets = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                AssetRegistration asset = new AssetRegistration();
                asset.setName(record.get("name"));
                asset.setVendor(record.get("vendor"));
                asset.setPrice(Double.parseDouble(record.get("price")));
                asset.setStatus(record.get("status"));

                asset.setBarcode("ASSET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

                asset.setPurchasedDate(LocalDate.parse(record.get("purchasedDate")));
                asset.setWarrantyStartDate(LocalDate.parse(record.get("warrantyStartDate")));
                asset.setWarrantyRenewalDate(LocalDate.parse(record.get("warrantyRenewalDate")));

                if (record.get("categoryType").equalsIgnoreCase("Hardware")) {
                    HardwareDetails hardwareDetails = new HardwareDetails();
                    hardwareDetails.setSerialNumber(record.get("serialNumber"));
                    hardwareDetails.setSpecifications(record.get("specifications"));
                    hardwareDetails.setBrand(record.get("brand"));
                    hardwareDetails.setType(record.get("type"));
                    asset.setHardwareDetails(hardwareDetails);
                }

                assets.add(asset);
            }

            return assets;

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
