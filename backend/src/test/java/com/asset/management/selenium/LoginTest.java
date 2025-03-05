package com.asset.management.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    void testAdminLogin() {
        driver.get("http://localhost:5173");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        // Validate redirection to Admin Dashboard
        wait.until(ExpectedConditions.urlContains("asset-dashboard"));
        assertTrue(driver.getCurrentUrl().contains("asset-dashboard"));
    }

    @Test
    void testEmployeeLogin() {
        driver.get("http://localhost:5173");

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("employee@example.com"); // Any non-admin email
        passwordInput.sendKeys("anypassword");
        loginButton.click();

        // Validate redirection to Employee Dashboard
        wait.until(ExpectedConditions.urlContains("employee-dashboard"));
        assertTrue(driver.getCurrentUrl().contains("employee-dashboard"));
    }
}
