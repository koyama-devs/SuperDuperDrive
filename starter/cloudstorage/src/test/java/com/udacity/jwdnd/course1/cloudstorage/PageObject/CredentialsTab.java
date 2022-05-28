package com.udacity.jwdnd.course1.cloudstorage.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// page_url = about:blank
public class CredentialsTab {
    private WebDriver driver;

    public CredentialsTab(WebDriver driver) {
        this.driver = driver;
    }

    // The Credentials tab contains several HTML elements that will be represented as WebElements.
    private By addANewCredentialButton = By.id("add-credential-button");

    private By firstEditButton = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr[1]/td[1]/button");

    private By firstDeleteLink = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr[1]/td[1]/a");
    private By urlTextField = By.id("credential-url");
    private By usernameTextField = By.id("credential-username");

    private By passwordTextField = By.id("credential-password");
    private By saveChangesButton = By.id("credential-save-changes-button");

    private void fillCredentialModalAndSaveChanges(String url, String username, String password) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Fill Credential Url
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(urlTextField));
        WebElement inputUrl = driver.findElement(urlTextField);
        inputUrl.click();
        inputUrl.clear();
        inputUrl.sendKeys(url);

        // Fill Credential Username
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(usernameTextField));
        WebElement inputUsername = driver.findElement(usernameTextField);
        inputUsername.click();
        inputUsername.clear();
        inputUsername.sendKeys(username);

        // Fill Credential Password
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(passwordTextField));
        WebElement inputPassword = driver.findElement(passwordTextField);
        inputPassword.click();
        inputPassword.clear();
        inputPassword.sendKeys(password);

        // Click "Save changes"
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(saveChangesButton));
        WebElement buttonSaveChanges = driver.findElement(saveChangesButton);
        buttonSaveChanges.click();
    }

    // The Credentials tab allows the user to add a new credential
    public CredentialsTab addNewCredential(String url, String username, String password) {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click "+ Add a New Credential" button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(addANewCredentialButton));
        WebElement addCredentialButton = driver.findElement(addANewCredentialButton);
        addCredentialButton.click();

        fillCredentialModalAndSaveChanges(url, username, password);

        return this;
    }

    public CredentialsTab editFirstCredential(String url, String username, String password) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click "Edit" button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(firstEditButton));
        WebElement editBtn = driver.findElement(firstEditButton);
        editBtn.click();

        fillCredentialModalAndSaveChanges(url, username, password);

        return this;
    }

    public String deleteFirstCredential() {
        // Click the first "Delete" button
        WebElement deleteLink = getFirstDeleteLink();
        String credentialId = getCredentialIdOfDeleteLink(deleteLink);
        deleteLink.click();

        return credentialId;
    }

    public WebElement getFirstDeleteLink() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click the first "Delete" button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(firstDeleteLink));
        WebElement deleteLink = driver.findElement(firstDeleteLink);

        return deleteLink;
    }

    public String getCredentialIdOfDeleteLink(WebElement deleteLink) {
        String href = deleteLink.getAttribute("href");
        return href.substring(href.lastIndexOf('/') + 1);
    }
}