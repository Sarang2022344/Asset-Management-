package com.asset.management.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CombinedAssetDisposalTests{

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait

        // 🔹 Perform login once for all tests
        driver.get("http://localhost:5173");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        // Wait for login to complete
        wait.until(ExpectedConditions.urlContains("asset-dashboard"));
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeEach
    void navigateToAssetDisposalPage() {
        // 🔹 Wait for any overlapping elements (e.g., loading spinners) to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loading-spinner']")));

        // 🔹 Wait for the Asset Disposal link to be present
        WebElement assetDisposalMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Asset Disposal')]")));

        // 🔹 Scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", assetDisposalMenu);

        // 🔹 Wait for the element to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(assetDisposalMenu));

        // 🔹 Click the element using JavaScript (fallback)
        try {
            assetDisposalMenu.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", assetDisposalMenu);
        }

        // 🔹 Wait for the Asset Disposal page to load
        wait.until(ExpectedConditions.urlContains("asset-disposal"));
    }

    @Test
    void testAssetDisposalValidation() throws InterruptedException {
        // 🔹 Step 1: Click "Add Asset for Disposal" button
        WebElement addDisposalButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Add Asset for Disposal')]")));
        addDisposalButton.click();

        // 🔹 Step 2: Enter Invalid Inputs
        WebElement assetId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("assetId")));
        WebElement companyId = driver.findElement(By.name("companyId"));
        WebElement reason = driver.findElement(By.name("reason"));
        WebElement disposalDate = driver.findElement(By.name("date"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        assetId.sendKeys("abc");  // Invalid Asset ID (Should be numeric)
        Thread.sleep(500);
        companyId.sendKeys("xyz"); // Invalid Company ID (Should be numeric)
        Thread.sleep(500);
        disposalDate.sendKeys("01-01-2020"); // Past Date
        Thread.sleep(500);

        // 🔹 Step 2.1: Select a valid reason (Dropdown or Text Field)
        try {
            Select reasonDropdown = new Select(reason);
            reasonDropdown.selectByVisibleText("Obsolete");
            System.out.println("Selected reason from dropdown: Obsolete");
        } catch (UnexpectedTagNameException e) {
            reason.clear();
            reason.sendKeys("Damaged beyond repair");
            System.out.println("Entered reason in text field: Damaged beyond repair");
        }

        submitButton.click();

        // 🔹 Step 3: Validate At Least One Error Message
        try {
            List<WebElement> errorMessages = driver.findElements(By.xpath("//span[contains(@class,'error-text')]"));

            boolean errorDisplayed = false;
            for (WebElement error : errorMessages) {
                if (error.isDisplayed()) {
                    System.out.println("Found validation error: " + error.getText());
                    errorDisplayed = true;
                }
            }

            assertTrue(errorDisplayed, "No validation messages found!");
            System.out.println("At least one validation message is displayed correctly!");

        } catch (NoSuchElementException e) {
            fail("No validation messages found!");
        }

        // 🔹 Step 4: Close the Form
        Thread.sleep(2000); // Small delay before searching for the Cancel button

        try {
            WebElement cancelButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Cancel')]")));
            System.out.println("Cancel button found!");

            // Ensure Cancel button is visible and clickable
            wait.until(ExpectedConditions.visibilityOf(cancelButton));
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cancelButton);

            // Try clicking using Actions class
            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(cancelButton).click().perform();
                System.out.println("Cancel button clicked using Actions!");
            } catch (ElementClickInterceptedException e) {
                // Fallback to JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelButton);
                System.out.println("Cancel button clicked using JavaScript!");
            }

        } catch (TimeoutException e) {
            fail("Timeout: Cancel button not found in time! Check if it exists in the DOM.");
        } catch (NoSuchElementException e) {
            fail("Cancel button does not exist in the DOM!");
        }
    }

    @Test
    void testDuplicateAssetDisposalNotAllowed() throws InterruptedException {
        // 🔹 Step 1: Wait for the overlay to disappear
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='popup-form-overlay']")));
            System.out.println("Overlay is no longer present.");
        } catch (TimeoutException e) {
            System.out.println("Overlay did not disappear. Proceeding with JavaScript click.");
        }

        // 🔹 Step 2: Click "Add Asset for Disposal" button using JavaScript
        WebElement addDisposalButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Add Asset for Disposal')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addDisposalButton);
        System.out.println("'Add Asset for Disposal' button clicked using JavaScript.");

        // 🔹 Step 3: Wait for the popup form to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("popup-form")));
        System.out.println("Popup form is visible.");

        // 🔹 Step 4: Fill in the form
        WebElement assetId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("assetId")));
        WebElement companyId = driver.findElement(By.name("companyId"));
        WebElement reason = driver.findElement(By.name("reason"));
        WebElement disposalDate = driver.findElement(By.name("date"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        assetId.sendKeys("8"); // Assume asset ID 8 exists in Asset Registration
        Thread.sleep(1000);
        companyId.sendKeys("1");
        Thread.sleep(1000);
        new Select(reason).selectByVisibleText("Upgrade");
        Thread.sleep(1000);
        disposalDate.sendKeys("21-03-2025");
        Thread.sleep(1000);
        submitButton.click();
        System.out.println("Form submitted.");

        // 🔹 Step 5: Handle Alert for First Disposal
        try {
            Thread.sleep(3000); // Allow time for the alert to appear
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("First Disposal Alert: " + alert.getText());
            alert.accept();
        } catch (TimeoutException e) {
            System.out.println("Alert not found. Checking if the table updated.");

            // If no alert appears, verify disposal was recorded in the table
            WebElement table = driver.findElement(By.xpath("//table/tbody"));
            if (!table.getText().contains("End of Life")) {
                fail("First disposal success alert not found and asset disposal was not recorded in the table!");
            }
        }

        // 🔹 Step 6: Ensure Popup Form is Closed
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("popup-form")));
            System.out.println("Popup form is closed.");
        } catch (TimeoutException e) {
            System.out.println("Popup form did not close properly. Attempting to close manually.");
            try {
                WebElement closeButton = driver.findElement(By.xpath("//button[contains(text(),'Cancel')]"));
                closeButton.click();
                System.out.println("Form closed manually.");
            } catch (NoSuchElementException ex) {
                System.out.println("Close button not found. Form might have closed automatically.");
            }
        }

        // 🔹 Step 7: Verify Disposal Entry Appears in Table
        WebElement table = driver.findElement(By.xpath("//table/tbody"));
        boolean isAdded = false;
        for (int i = 0; i < 5; i++) { // Retry for 10 seconds
            Thread.sleep(2000);
            table = driver.findElement(By.xpath("//table/tbody"));
            if (table.getText().contains("End of Life")) {
                isAdded = true;
                break;
            }
        }
        assertTrue(isAdded, "Newly added disposal 'End of Life' not found in the table!");
        System.out.println("Disposal entry found in the table.");

        // 🔹 Step 8: Attempt to Add the Same Asset Again
        addDisposalButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("popup-form")));
        System.out.println("Popup form is visible again.");

        assetId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("assetId")));
        companyId = driver.findElement(By.name("companyId"));
        reason = driver.findElement(By.name("reason"));
        disposalDate = driver.findElement(By.name("date"));
        submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        assetId.sendKeys("8"); // Using the same Asset ID again
        System.out.println("Asset ID entered again.");
        companyId.sendKeys("1");
        System.out.println("Company ID entered again.");
        new Select(reason).selectByVisibleText("Upgrade");
        System.out.println("Reason selected again.");
        disposalDate.sendKeys("5-03-2025");
        System.out.println("Disposal date entered again.");
        submitButton.click();
        System.out.println("Form submitted again.");

        // 🔹 Step 9: Verify Error Message for Duplicate Disposal
        try {
            Thread.sleep(5000); // Increased wait time for alert
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Duplicate Disposal Alert: " + alertText);
            assertTrue(alertText.contains("Asset is already disposed"), "Expected error message not found!");
            alert.accept();
        } catch (TimeoutException e) {
            // Check for error message on the page if alert is not found
            try {
                WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Asset is already disposed')]")));
                System.out.println("Duplicate Disposal Error Message: " + errorMessage.getText());
                assertTrue(errorMessage.getText().contains("Asset is already disposed"), "Expected error message not found!");
            } catch (TimeoutException ex) {
                fail("Duplicate disposal error alert or message not found!");
            }
        }

        System.out.println("Test Passed: System Prevents Duplicate Asset Disposal.");
    }
}