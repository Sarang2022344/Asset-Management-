package com.asset.management.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssetDisposalTest extends BaseTest {

    @Test
    void testAssetDisposalAndEdit() throws InterruptedException {
        driver.get("http://localhost:5173");

        // Step 1: Login as Admin
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        Thread.sleep(1000);
        passwordInput.sendKeys("anypassword");
        Thread.sleep(1000);
        loginButton.click();

        // Step 2: Wait for Dashboard to Load
        wait.until(ExpectedConditions.urlContains("asset-dashboard"));

        // Step 3: Click "Asset Disposal" from Sidebar
        WebElement assetDisposalMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Asset Disposal')]")));
        assetDisposalMenu.click();

        // Step 4: Wait for Asset Disposal Page
        wait.until(ExpectedConditions.urlContains("asset-disposal"));

        // Step 5: Click "Add Asset for Disposal" button
        WebElement addDisposalButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Add Asset for Disposal')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addDisposalButton);
        addDisposalButton.click();

        // Step 6: Wait for Modal to Open
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("popup-form")));

        // Step 7: Fill the Asset Disposal Form
        WebElement assetId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("assetId")));
        WebElement companyId = driver.findElement(By.name("companyId"));
        WebElement reason = driver.findElement(By.name("reason"));
        WebElement disposalDate = driver.findElement(By.name("date"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        assetId.sendKeys("10");
        Thread.sleep(1000);
        companyId.sendKeys("1");
        Thread.sleep(1000);
        new Select(reason).selectByVisibleText("End of Life");
        Thread.sleep(1000);
        disposalDate.sendKeys("5-10-2025");
        Thread.sleep(1000);
        submitButton.click();

        // Step 8: Handle Alert Popup
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert Text: " + alert.getText());
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert found.");
        }

        // Step 9: Wait for the Table to Refresh
        boolean isAdded = false;
        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            if (driver.findElement(By.xpath("//table/tbody")).getText().contains("End of Life")) {
                isAdded = true;
                break;
            }
        }
        assertTrue(isAdded, "Newly added disposal 'End of Life' not found!");

        // Step 10: Find and Click "Edit" Button for the Newly Added Disposal
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//table/tbody/tr[td[contains(text(),'10')]]/td/button[contains(text(),'Edit')]")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);
        Thread.sleep(1000);
        editButton.click();

        // Step 11: Wait for Edit Modal to Open
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("popup-form")));

        // Step 12: Update the Reason and Date
        WebElement editReason = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("reason")));
        WebElement editDate = driver.findElement(By.name("date"));
        WebElement editSubmitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        new Select(editReason).selectByVisibleText("Damaged");
        Thread.sleep(1000);
        editDate.clear();
        editDate.sendKeys("10-04-2025");
        Thread.sleep(1000);
        editSubmitButton.click();

        // Step 13: Handle Alert Popup after Edit
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert Text: " + alert.getText());
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert found.");
        }

        // Step 14: Wait for the Table to Refresh
        boolean isUpdated = false;
        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            if (driver.findElement(By.xpath("//table/tbody")).getText().contains("Damaged")) {
                isUpdated = true;
                break;
            }
        }
        assertTrue(isUpdated, "The updated reason 'Damaged' was not found in the table!");

        // Keep the browser open for manual verification
        Thread.sleep(5000);

        System.out.println("Test Passed: Asset Disposal Added & Edited Successfully");
    }



}