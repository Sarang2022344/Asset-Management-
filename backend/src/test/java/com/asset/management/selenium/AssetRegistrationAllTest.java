package com.asset.management.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssetRegistrationAllTest extends BaseTest {

    @Test
    void testAssetRegistrationScenarios() {
        navigateToAssetRegistration();

        //  Valid Asset Registration
        registerAsset("Lenovo ES", "1", "Hardware", "SN12345", "16GB RAM, 512GB SSD",
                "Dell", "Laptop", "Tech Supplier", "75000", "Available",  true);
        verifyAssetInTable("Lenovo ES");

        //  Missing Asset Name
        registerAsset("", "1", "Hardware", "SN67890", "16GB RAM, 512GB SSD",
                "HP", "Laptop", "Tech Supplier", "75000", "Available",  false);
        verifyErrorMessage("Asset Name is required");

        //  Invalid Name (Contains Numbers)
        registerAsset("Laptop123", "1", "Hardware", "SN67890", "16GB RAM, 512GB SSD",
                "HP", "Laptop", "Tech Supplier", "75000", "Available",  false);
        verifyErrorMessage("Asset Name should not contain numbers");

        // Invalid Price (0 or Negative)
        registerAsset("Invalid Price Test", "1", "Hardware", "SN99999", "16GB RAM, 512GB SSD",
                "HP", "Laptop", "Tech Supplier", "-500", "Available",  false);
        verifyErrorMessage("Price must be greater than 0");

        //  Missing Serial Number
        registerAsset("No Serial Test", "1", "Hardware", "", "16GB RAM, 512GB SSD",
                "Lenovo", "Laptop", "Tech Supplier", "75000", "Available",  false);
        verifyErrorMessage("Serial Number is required");

        //  Missing Specifications
        registerAsset("No Spec Test", "1", "Hardware", "SN44444", "",
                "Lenovo", "Laptop", "Tech Supplier", "75000", "Available",  false);
        verifyErrorMessage("Specifications are required");


    }

    // **🔹 Navigate to Asset Registration Page**
    void navigateToAssetRegistration() {
        driver.get("http://localhost:5173");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("asset-dashboard"));

        WebElement assetRegistrationMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Asset Registration')]")));
        assetRegistrationMenu.click();

        wait.until(ExpectedConditions.urlContains("asset-registration"));
    }

    // **🔹 Register an Asset**
    void registerAsset(String name, String companyId, String category, String serialNumber, String specifications,
                       String brand, String type, String vendor, String price, String status,  boolean expectSuccess) {
        WebElement registerAssetButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Add Asset')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerAssetButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerAssetButton);

        WebElement assetName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        WebElement companyField = driver.findElement(By.name("companyId"));
        WebElement categoryField = driver.findElement(By.name("categoryId"));
        WebElement serialNumberField = driver.findElement(By.name("serialNumber"));
        WebElement specificationsField = driver.findElement(By.name("specifications"));
        WebElement brandField = driver.findElement(By.name("brand"));
        WebElement typeField = driver.findElement(By.name("type"));
        WebElement vendorField = driver.findElement(By.name("vendor"));
        WebElement priceField = driver.findElement(By.name("price"));
        WebElement statusField = driver.findElement(By.name("status"));

        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        assetName.sendKeys(name);
        companyField.sendKeys(companyId);
        categoryField.sendKeys(category);
        serialNumberField.sendKeys(serialNumber);
        specificationsField.sendKeys(specifications);
        brandField.sendKeys(brand);
        typeField.sendKeys(type);
        vendorField.sendKeys(vendor);
        priceField.sendKeys(price);
        statusField.sendKeys(status);



        submitButton.click();

        if (expectSuccess) {
            handleAlert("Asset added successfully!");
        }
    }

    // ** Handle Alerts**
    void handleAlert(String expectedText) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            assertTrue(alertText.contains(expectedText));
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert found.");
        }
    }

    // **🔹 Verify Asset in Table**
    void verifyAssetInTable(String assetName) {
        WebElement assetTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        assertTrue(assetTable.getText().contains(assetName));
    }

    // **🔹 Verify Error Messages**
    void verifyErrorMessage(String expectedError) {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("text-danger")));
        assertTrue(errorMsg.getText().contains(expectedError));
    }
}
