package com.asset.management.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssetRegistrationTest extends BaseTest {

    @Test
    void testRegisterAsset() {
        driver.get("http://localhost:5173");

        // Step 1: Login as Admin
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        // Step 2: Wait for Admin Dashboard
        wait.until(ExpectedConditions.urlContains("asset-dashboard"));

        // Step 3: Click "Asset Registration" from Sidebar
        WebElement assetRegistrationMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Asset Registration')]")));
        assetRegistrationMenu.click();

        // Step 4: Wait for Asset Registration Page
        wait.until(ExpectedConditions.urlContains("asset-registration"));

        // Step 5: Click "Add Asset" button (wait & scroll)
        WebElement registerAssetButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Add Asset')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerAssetButton);
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registerAssetButton.click();

        // Step 6: Wait for Modal to Open
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));

        // Step 7: Fill the Asset Registration Form
        WebElement assetName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        WebElement companyId = driver.findElement(By.name("companyId"));
        WebElement category = driver.findElement(By.name("categoryId"));
        WebElement vendor = driver.findElement(By.name("vendor"));
        WebElement price = driver.findElement(By.name("price"));
        WebElement status = driver.findElement(By.name("status"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        assetName.sendKeys("Lenovo ES");
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        companyId.sendKeys("1");
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        category.sendKeys("Hardware");
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vendor.sendKeys("Tech Supplier");
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        price.sendKeys("75000");
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        status.sendKeys("Available");
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        submitButton.click();

        // Step 8: Handle Alert Popup (Asset Added Successfully)
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert Text: " + alert.getText()); // Debugging
            alert.accept(); // Click OK to dismiss alert
        } catch (Exception e) {
            System.out.println("No alert found.");
        }
        try {
            Thread.sleep(700); // Small delay before clicking
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Step 9: Verify Asset in Table
        WebElement assetTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        assertTrue(assetTable.getText().contains("Laptop"));
    }
}

