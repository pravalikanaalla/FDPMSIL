package com.ccms.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

/**
 * Base test class that handles WebDriver setup and teardown.
 *
 * - Uses WebDriverManager for automatic driver management (no hardcoded paths)
 * - Supports Chrome (default) and Firefox via TestNG parameters
 * - Provides a shared WebDriver instance and wait timeout for all tests
 */
public class BaseTest {

    protected WebDriver driver;
    protected static final Duration WAIT_TIMEOUT = Duration.ofSeconds(15);
    protected static final String CCMS_URL = "https://msilpoc.fdpconnect.com/";

    /**
     * Setup: Initialize WebDriver before each test method.
     *
     * @param browser "chrome" (default) or "firefox"
     */
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            // Remove --headless if you want to see the browser window
            // options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            // Remove --headless if you want to see the browser window
            // options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    /**
     * Navigate to the CCMS login page.
     */
    protected void navigateToLoginPage() {
        driver.get(CCMS_URL);
    }

    /**
     * Teardown: Quit the WebDriver after each test method.
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
