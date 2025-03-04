package com.asset.management.selenium;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private static WebDriver driver;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    void testAdminLogin() throws InterruptedException {
        driver.get("http://localhost:5173");

        WebElement emailInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("admin@example.com");
        Thread.sleep(1000);
        passwordInput.sendKeys("anypassword");
        Thread.sleep(1000);
        loginButton.click();

        Thread.sleep(1000);

        // Validate redirection to Admin Dashboard
        assertTrue(driver.getCurrentUrl().contains("asset-dashboard"));
    }

    @Test
    void testEmployeeLogin() throws InterruptedException {
        driver.get("http://localhost:5173");

        WebElement emailInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("login-btn"));

        emailInput.sendKeys("employee@example.com");
        Thread.sleep(1000);// Any non-admin email
        passwordInput.sendKeys("anypassword");
        Thread.sleep(1000);
        loginButton.click();

        Thread.sleep(1000);

        // Validate redirection to Employee Dashboard
        assertTrue(driver.getCurrentUrl().contains("employee-dashboard"));
    }


    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}

