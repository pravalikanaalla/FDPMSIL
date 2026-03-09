package com.ccms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for the CCMS Login Page.
 *
 * Encapsulates all locators and actions for the login page at
 * https://msilpoc.fdpconnect.com/
 *
 * Using stable CSS/XPath selectors instead of React auto-generated IDs.
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ========================
    // Locators
    // ========================
    private final By usernameField = By.cssSelector("input[type='email'], input[type='text']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By loginButton = By.xpath("//button[text()='Login']");
    private final By errorMessage = By.cssSelector(".error, .alert, [role='alert'], .MuiAlert-root");
    private final By errorText = By.xpath(
            "//*[contains(text(),'Invalid') or contains(text(),'invalid') " +
            "or contains(text(),'error') or contains(text(),'Error') " +
            "or contains(text(),'incorrect') or contains(text(),'Incorrect') " +
            "or contains(text(),'wrong') or contains(text(),'Wrong')]"
    );

    // ========================
    // Constructor
    // ========================
    public LoginPage(WebDriver driver, Duration waitTimeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, waitTimeout);
    }

    // ========================
    // Actions
    // ========================

    /**
     * Wait for the login page to fully load.
     */
    public LoginPage waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        return this;
    }

    /**
     * Enter the username/email.
     */
    public LoginPage enterUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        field.clear();
        field.sendKeys(username);
        return this;
    }

    /**
     * Enter the password.
     */
    public LoginPage enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        field.clear();
        field.sendKeys(password);
        return this;
    }

    /**
     * Click the Login button.
     */
    public void clickLogin() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        button.click();
    }

    /**
     * Perform a full login: enter username, enter password, click Login.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // ========================
    // Verifications
    // ========================

    /**
     * Check if the username field is displayed.
     */
    public boolean isUsernameFieldDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the password field is displayed.
     */
    public boolean isPasswordFieldDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the Login button is displayed.
     */
    public boolean isLoginButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if an error message is displayed on the page.
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(errorMessage),
                    ExpectedConditions.visibilityOfElementLocated(errorText)
            )) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the current page URL.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Get the page title.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}
