package com.asset.management.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.Assertions;

public class AssetAllocationTest extends BaseTest {

    @Test
    void testValidAssetAllocation() {
        driver.get("http://localhost:5173");

        // Step 1: Login as Admin
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        try { Thread.sleep(700); } catch (InterruptedException e) { e.printStackTrace(); }
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        // Step 2: Wait for Admin Dashboard
        wait.until(ExpectedConditions.urlContains("asset-dashboard"));

        // Step 3: Click "Asset Allocation" from Sidebar
        WebElement assetAllocationMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Asset Allocation')]")));
        assetAllocationMenu.click();

        // Step 4: Wait for Asset Allocation Page
        wait.until(ExpectedConditions.urlContains("asset-allocation"));

        // Step 5: Fill the Asset Allocation Form (Valid Data)
        WebElement barcodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("barcode")));
        WebElement employeeIdInput = driver.findElement(By.id("employeeId"));
        WebElement userIdInput = driver.findElement(By.id("userId"));
        WebElement allocateButton = driver.findElement(By.xpath("//button[text()='Allocate Asset']"));

        barcodeInput.sendKeys("A1234");
        employeeIdInput.sendKeys("101");
        userIdInput.sendKeys("501");
        allocateButton.click();

        // Step 6: Verify Asset Allocation Success
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        Assertions.assertTrue(successMessage.getText().contains("✅"), "Success message not displayed!");
    }

    @Test
    void testInvalidAssetAllocation() {
        driver.get("http://localhost:5173");

        // Login as Admin
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("asset-dashboard"));

        // Navigate to Asset Allocation
        WebElement assetAllocationMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Asset Allocation')]")));
        assetAllocationMenu.click();
        wait.until(ExpectedConditions.urlContains("asset-allocation"));

        // Enter Invalid Asset Barcode
        WebElement barcodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("barcode")));
        WebElement employeeIdInput = driver.findElement(By.id("employeeId"));
        WebElement userIdInput = driver.findElement(By.id("userId"));
        WebElement allocateButton = driver.findElement(By.xpath("//button[text()='Allocate Asset']"));

        barcodeInput.sendKeys("INVALID123");
        employeeIdInput.sendKeys("101");
        userIdInput.sendKeys("501");
        allocateButton.click();

        // Verify Error Message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger")));
        Assertions.assertTrue(errorMessage.getText().contains("Asset not found"), "Error message not displayed for invalid asset!");
    }

    @Test
    void testEmptyFieldsAssetAllocation() {
        driver.get("http://localhost:5173");

        // Login as Admin
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("asset-dashboard"));

        // Navigate to Asset Allocation
        WebElement assetAllocationMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Asset Allocation')]")));
        assetAllocationMenu.click();
        wait.until(ExpectedConditions.urlContains("asset-allocation"));

        // Click Allocate Asset Without Filling Fields
        WebElement allocateButton = driver.findElement(By.xpath("//button[text()='Allocate Asset']"));
        allocateButton.click();

        // Verify Error Message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger")));
        Assertions.assertTrue(errorMessage.getText().contains("All fields are required"), "Error message not displayed for empty fields!");
    }

    @Test
    void testDuplicateAssetAllocation() {
        driver.get("http://localhost:5173");

        // Login as Admin
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("asset-dashboard"));

        // Navigate to Asset Allocation
        WebElement assetAllocationMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Asset Allocation')]")));
        assetAllocationMenu.click();
        wait.until(ExpectedConditions.urlContains("asset-allocation"));

        // Enter Valid Asset Details for First Allocation
        WebElement barcodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("barcode")));
        WebElement employeeIdInput = driver.findElement(By.id("employeeId"));
        WebElement userIdInput = driver.findElement(By.id("userId"));
        WebElement allocateButton = driver.findElement(By.xpath("//button[text()='Allocate Asset']"));

        barcodeInput.sendKeys("ASSET-25873E75");
        employeeIdInput.sendKeys("1");
        userIdInput.sendKeys("1");
        allocateButton.click();

        // Wait for success message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));

        // Try Allocating the Same Asset Again
        barcodeInput.clear();
        employeeIdInput.clear();
        userIdInput.clear();

        barcodeInput.sendKeys("A1234");
        employeeIdInput.sendKeys("102");
        userIdInput.sendKeys("502");
        allocateButton.click();

        // Verify Error Message for Duplicate Allocation
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger")));
        Assertions.assertTrue(errorMessage.getText().contains("Asset already allocated"), "Error message not displayed for duplicate allocation!");
    }
}

