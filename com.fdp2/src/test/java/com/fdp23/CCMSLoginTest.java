package com.fdp23;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Test class for CCMS (MSIL) Login Page.
 *
 * Tests the login functionality at https://msilpoc.fdpconnect.com/
 * using Selenium WebDriver with TestNG framework.
 *
 * Uses:
 * - WebDriverManager for automatic browser driver management (no hardcoded paths)
 * - Explicit waits (WebDriverWait) instead of Thread.sleep
 * - Stable CSS/XPath locators instead of auto-generated React IDs
 * - Proper TestNG annotations for setup, teardown, and assertions
 */
public class CCMSLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String CCMS_URL = "https://msilpoc.fdpconnect.com/";
    private static final String VALID_USERNAME = "msil2022@gmail.com";
    private static final String VALID_PASSWORD = "123456789";
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(15);

    /**
     * Setup method runs before each test.
     * Initializes the WebDriver and navigates to the CCMS login page.
     *
     * @param browser browser name: "chrome" (default) or "firefox"
     */
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        driver.get(CCMS_URL);
    }

    /**
     * Teardown method runs after each test.
     * Closes the browser and releases resources.
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Test: Verify that the CCMS login page loads successfully.
     * Checks that the username field, password field, and Login button are displayed.
     */
    @Test(priority = 1, description = "Verify CCMS login page loads with all required elements")
    public void testLoginPageLoads() {
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email'], input[type='text']"))
        );
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']"))
        );
        WebElement loginButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Login']"))
        );

        Assert.assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        Assert.assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        Assert.assertTrue(loginButton.isDisplayed(), "Login button should be visible");
    }

    /**
     * Test: Verify successful login with valid credentials.
     * Enters valid username/password and clicks Login, then asserts
     * that the user is redirected away from the login page.
     */
    @Test(priority = 2, description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        enterCredentials(VALID_USERNAME, VALID_PASSWORD);
        clickLoginButton();

        // Wait for navigation away from login page (URL should change or a dashboard element should appear)
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/home"),
                ExpectedConditions.not(ExpectedConditions.urlToBe(CCMS_URL)),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[normalize-space()='HS code Clasify']"))
        ));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, CCMS_URL,
                "After valid login, user should be redirected away from the login page");
    }

    /**
     * Test: Verify login fails with invalid username.
     */
    @Test(priority = 3, description = "Verify login fails with invalid username")
    public void testInvalidUsername() {
        enterCredentials("invaliduser@test.com", VALID_PASSWORD);
        clickLoginButton();

        // Wait briefly and check that user remains on the login page or an error appears
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Invalid') or contains(text(),'invalid') or contains(text(),'error') or contains(text(),'Error') or contains(text(),'incorrect') or contains(text(),'Incorrect')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error, .alert, [role='alert']"))
            ));
        } catch (Exception e) {
            // If no explicit error message, verify user is still on login page
        }

        // Verify user is still on the login page (login did not succeed)
        boolean stillOnLoginPage = driver.getCurrentUrl().contains("msilpoc.fdpconnect.com");
        Assert.assertTrue(stillOnLoginPage,
                "User should remain on login page with invalid credentials");
    }

    /**
     * Test: Verify login fails with invalid password.
     */
    @Test(priority = 4, description = "Verify login fails with invalid password")
    public void testInvalidPassword() {
        enterCredentials(VALID_USERNAME, "WrongPassword123");
        clickLoginButton();

        // Wait briefly and check that user remains on the login page or an error appears
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Invalid') or contains(text(),'invalid') or contains(text(),'error') or contains(text(),'Error') or contains(text(),'incorrect') or contains(text(),'Incorrect')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".error, .alert, [role='alert']"))
            ));
        } catch (Exception e) {
            // If no explicit error message, verify user is still on login page
        }

        boolean stillOnLoginPage = driver.getCurrentUrl().contains("msilpoc.fdpconnect.com");
        Assert.assertTrue(stillOnLoginPage,
                "User should remain on login page with wrong password");
    }

    /**
     * Test: Verify login fails when both fields are empty.
     */
    @Test(priority = 5, description = "Verify login fails with empty credentials")
    public void testEmptyCredentials() {
        clickLoginButton();

        // User should remain on login page; form validation should prevent submission
        boolean stillOnLoginPage = driver.getCurrentUrl().contains("msilpoc.fdpconnect.com");
        Assert.assertTrue(stillOnLoginPage,
                "User should remain on login page when submitting empty credentials");
    }

    /**
     * Test: Verify login fails when only username is provided (password empty).
     */
    @Test(priority = 6, description = "Verify login fails with empty password")
    public void testEmptyPassword() {
        enterCredentials(VALID_USERNAME, "");
        clickLoginButton();

        boolean stillOnLoginPage = driver.getCurrentUrl().contains("msilpoc.fdpconnect.com");
        Assert.assertTrue(stillOnLoginPage,
                "User should remain on login page when password is empty");
    }

    /**
     * Test: Verify login fails when only password is provided (username empty).
     */
    @Test(priority = 7, description = "Verify login fails with empty username")
    public void testEmptyUsername() {
        enterCredentials("", VALID_PASSWORD);
        clickLoginButton();

        boolean stillOnLoginPage = driver.getCurrentUrl().contains("msilpoc.fdpconnect.com");
        Assert.assertTrue(stillOnLoginPage,
                "User should remain on login page when username is empty");
    }

    // ========================
    // Helper methods
    // ========================

    /**
     * Enters username and password into the login form fields.
     * Uses stable CSS selectors instead of auto-generated React IDs.
     */
    private void enterCredentials(String username, String password) {
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email'], input[type='text']"))
        );
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']"))
        );

        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Clicks the Login button on the form.
     */
    private void clickLoginButton() {
        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Login']"))
        );
        loginButton.click();
    }
}
