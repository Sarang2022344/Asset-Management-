package com.asset.management.util;

import com.asset.management.exception.CSVProcessingException;
import com.asset.management.model.AssetRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVHelperTest {

    private static final String VALID_CSV = """
            name,vendor,price,status,purchasedDate,warrantyStartDate,warrantyRenewalDate,categoryType,serialNumber,specifications,brand,type
            Laptop,Dell,50000,Active,2023-01-01,2023-01-05,2025-01-05,Hardware,12345,16GB RAM,HP,Gaming
            """;

    private static final String INVALID_CSV = """
            name,vendor,price
            Laptop,Dell,NotANumber
            """;

    @BeforeEach
    void setUp() {
        // You can initialize test-specific setup if needed
    }

    @Test
    void testHasCSVFormat_ValidCSV() {
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", VALID_CSV.getBytes());
        assertTrue(CSVHelper.hasCSVFormat(file));
    }

    @Test
    void testHasCSVFormat_InvalidCSV() {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Invalid content".getBytes());
        assertFalse(CSVHelper.hasCSVFormat(file));
    }

    @Test
    void testHasCSVFormat_NullFilename() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn(null);
        Mockito.when(mockFile.getContentType()).thenReturn(null);

        assertFalse(CSVHelper.hasCSVFormat(mockFile));
    }

    @Test
    void testCsvToAssets_ValidData() {
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(VALID_CSV.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        List<AssetRegistration> assets = CSVHelper.csvToAssets(reader);

        assertNotNull(assets);
        assertEquals(1, assets.size());
        assertEquals("Laptop", assets.get(0).getName());
        assertEquals(50000, assets.get(0).getPrice());
        assertNotNull(assets.get(0).getBarcode());
    }

    @Test
    void testCsvToAssets_InvalidData() {
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(INVALID_CSV.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        assertThrows(NumberFormatException.class, () -> CSVHelper.csvToAssets(reader));
    }

    @Test
    void testCsvToAssets_Exception() {
        assertThrows(CSVProcessingException.class, () -> CSVHelper.csvToAssets(null));
    }
}
