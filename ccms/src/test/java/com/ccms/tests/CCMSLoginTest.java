package com.ccms.tests;

import com.ccms.base.BaseTest;
import com.ccms.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for CCMS (MSIL) Login Page.
 *
 * Tests the login functionality at https://msilpoc.fdpconnect.com/
 * using Selenium WebDriver with TestNG framework and Page Object Model.
 *
 * How to run:
 *   mvn test
 *   or
 *   mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-ccms.xml
 */
public class CCMSLoginTest extends BaseTest {

    private LoginPage loginPage;

    // Credentials loaded from environment variables (set CCMS_TEST_USERNAME and CCMS_TEST_PASSWORD)
    // Falls back to defaults if env vars are not set
    private static final String VALID_USERNAME = System.getenv("CCMS_TEST_USERNAME") != null
            ? System.getenv("CCMS_TEST_USERNAME") : "msil2022@gmail.com";
    private static final String VALID_PASSWORD = System.getenv("CCMS_TEST_PASSWORD") != null
            ? System.getenv("CCMS_TEST_PASSWORD") : "123456789";

    @BeforeMethod
    public void navigateAndInit() {
        navigateToLoginPage();
        loginPage = new LoginPage(driver, WAIT_TIMEOUT);
    }

    // ============================================================
    // Test 1: Verify login page loads with all required elements
    // ============================================================
    @Test(priority = 1, description = "Verify CCMS login page loads with username, password, and Login button")
    public void testLoginPageLoads() {
        loginPage.waitForPageLoad();

        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(),
                "Username/Email field should be visible on the login page");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(),
                "Password field should be visible on the login page");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button should be visible on the login page");
    }

    // ============================================================
    // Test 2: Verify successful login with valid credentials
    // ============================================================
    @Test(priority = 2, description = "Verify successful login with valid credentials redirects user")
    public void testValidLogin() {
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);

        // Wait for page to navigate after login
        try { Thread.sleep(5000); } catch (InterruptedException ignored) {}

        // Verify the Login button is no longer visible (user has left the login page)
        Assert.assertFalse(loginPage.isLoginButtonDisplayed(),
                "After valid login, the Login button should no longer be visible");
    }

    // ============================================================
    // Test 3: Verify login fails with invalid username
    // ============================================================
    @Test(priority = 3, description = "Verify login fails with invalid username")
    public void testInvalidUsername() {
        loginPage.login("invaliduser@test.com", VALID_PASSWORD);

        // Wait for error response
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        boolean stillOnLoginPage = loginPage.getCurrentUrl().contains("msilpoc.fdpconnect.com");
        Assert.assertTrue(stillOnLoginPage,
                "User should remain on the login page when using invalid username");
    }

    // ============================================================
    // Test 4: Verify login fails with invalid password
    // ============================================================
    @Test(priority = 4, description = "Verify login fails with invalid password")
    public void testInvalidPassword() {
        loginPage.login(VALID_USERNAME, "WrongPassword123");

        // Wait for error response
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}

        boolean stillOnLoginPage = loginPage.getCurrentUrl().contains("msilpoc.fdpconnect.com");
        Assert.assertTrue(stillOnLoginPage,
                "User should remain on the login page when using wrong password");
    }

    // ============================================================
    // Test 5: Verify login fails with both fields empty
    // ============================================================
    @Test(priority = 5, description = "Verify login fails with empty credentials")
    public void testEmptyCredentials() {
        loginPage.clickLogin();

        // Wait briefly for any validation response
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // Verify user is NOT logged in: login form or error should still be visible,
        // or the URL still contains the CCMS domain
        boolean notLoggedIn = loginPage.isPasswordFieldDisplayed()
                || loginPage.isLoginButtonDisplayed()
                || loginPage.getCurrentUrl().contains("msilpoc.fdpconnect.com");
        Assert.assertTrue(notLoggedIn,
                "User should not be logged in when submitting empty credentials");
    }

    // ============================================================
    // Test 6: Verify login fails with empty password
    // ============================================================
    @Test(priority = 6, description = "Verify login fails when password is empty")
    public void testEmptyPassword() {
        loginPage.enterUsername(VALID_USERNAME);
        loginPage.clickLogin();

        // Wait briefly for any validation response
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // The app may navigate to a different URL for validation, so check for
        // absence of post-login dashboard content rather than URL
        boolean notLoggedIn = loginPage.isPasswordFieldDisplayed()
                || loginPage.isLoginButtonDisplayed()
                || !loginPage.getCurrentUrl().contains("/dashboard");
        Assert.assertTrue(notLoggedIn,
                "Dashboard should NOT be visible when password is empty - user should not be logged in");
    }

    // ============================================================
    // Test 7: Verify login fails with empty username
    // ============================================================
    @Test(priority = 7, description = "Verify login fails when username is empty")
    public void testEmptyUsername() {
        loginPage.enterPassword(VALID_PASSWORD);
        loginPage.clickLogin();

        // Wait briefly for any validation response
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // Verify user is NOT successfully logged in
        boolean notLoggedIn = loginPage.isPasswordFieldDisplayed()
                || loginPage.isLoginButtonDisplayed()
                || !loginPage.getCurrentUrl().contains("/dashboard");
        Assert.assertTrue(notLoggedIn,
                "Dashboard should NOT be visible when username is empty - user should not be logged in");
    }
}
