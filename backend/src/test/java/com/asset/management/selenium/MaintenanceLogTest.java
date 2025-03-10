package com.asset.management.selenium;



import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaintenanceLogTest extends BaseTest {

    @Test
    void testMaintenanceScenarios() {
        navigateToAssetMaintenance();

        //  Valid Maintenance Request
        submitMaintenanceRequest("Lenovo Laptop", "Screen not working", "Urgent", true);
        verifyRequestInTable("Lenovo Laptop", "Pending");

        //  Missing Asset Name
        submitMaintenanceRequest("", "Battery issue", "Normal", false);
        verifyErrorMessage("Asset Name is required");

        //  Missing Issue Description
        submitMaintenanceRequest("HP Printer", "", "Urgent", false);
        verifyErrorMessage("Issue Description is required");

        //  Invalid Priority Selection
        submitMaintenanceRequest("Dell Monitor", "Display flickering", "", false);
        verifyErrorMessage("Priority selection is required");

        //  Admin Approving Maintenance Request
        adminApproveRequest("Lenovo Laptop");
        verifyRequestInTable("Lenovo Laptop", "In Progress");

        // Admin Closing the Maintenance Request
        adminCloseRequest("Lenovo Laptop");
        verifyRequestInTable("Lenovo Laptop", "Completed");
    }

    // Navigate to Asset Maintenance Page**
    void navigateToAssetMaintenance() {
        driver.get("http://localhost:8080");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("asset-dashboard"));

        WebElement maintenanceMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Asset Maintenance')]")));
        maintenanceMenu.click();

        wait.until(ExpectedConditions.urlContains("asset-maintenance"));
    }

    // Submit Maintenance Request**
    void submitMaintenanceRequest(String assetName, String issueDescription, String priority, boolean expectSuccess) {
        WebElement addRequestButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Raise Request')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addRequestButton);

        WebElement assetField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("assetName")));
        WebElement issueField = driver.findElement(By.name("issueDescription"));
        WebElement priorityDropdown = driver.findElement(By.name("priority"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        assetField.sendKeys(assetName);
        issueField.sendKeys(issueDescription);
        priorityDropdown.sendKeys(priority);
        submitButton.click();

        if (expectSuccess) {
            handleAlert("Maintenance request submitted successfully!");
        }
    }

    //  Admin Approves Maintenance Request**
    void adminApproveRequest(String assetName) {
        WebElement approveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[td[contains(text(),'" + assetName + "')]]//button[contains(text(),'Approve')]")));
        approveButton.click();
        handleAlert("Maintenance request approved!");
    }

    //  Admin Closes Maintenance Request**
    void adminCloseRequest(String assetName) {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[td[contains(text(),'" + assetName + "')]]//button[contains(text(),'Close')]")));
        closeButton.click();
        handleAlert("Maintenance request completed!");
    }

    //  Handle Alerts**
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

    //  Verify Maintenance Request in Table**
    void verifyRequestInTable(String assetName, String expectedStatus) {
        WebElement maintenanceTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        assertTrue(maintenanceTable.getText().contains(assetName));
        assertTrue(maintenanceTable.getText().contains(expectedStatus));
    }

    //  Verify Error Messages**
    void verifyErrorMessage(String expectedError) {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("text-danger")));
        assertTrue(errorMsg.getText().contains(expectedError));
    }
}
