package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupAndLoginFlowTests extends BaseTests{

    private void getSignupPage() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void testUnauthorizedUserAccess(){
        // Try to access homepage without logging in.
        driver.get("http://localhost:" + this.port);
        // Check we have been auto redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

        // Try to access signup page without logging in.
        getSignupPage();

        // Try to create any other random request without logging in.
        // And check we have been auto redirected to the log in page.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    private void doLogout()
    {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click logout button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
        WebElement loginButton = driver.findElement(By.id("logout-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Login"));

    }

    @Test
    public void testAuthorizedUserAccess(){
        // Create a test account
        doMockSignUp("New User","Test","NUT","123");
        doLogIn("NUT", "123");

        // Verifies that the home page is accessible
        Assertions.assertEquals("Home", driver.getTitle());

        doLogout();

        // Verifies that the home page is no longer accessible
        Assertions.assertNotEquals("Home", driver.getTitle());
        // Check we have been auto redirected to the log in page with message that "You have been logged out".
        Assertions.assertEquals("Login", driver.getTitle());
        Assertions.assertTrue(driver.findElement(By.id("logout-msg")).getText().contains("You have been logged out"));
    }
}
