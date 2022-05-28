package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.PageObject.CredentialsTab;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddEditDeleteCredentialTests extends BaseTests{

    private void doBeforeTest(String username) {
        // Create a test account
        doMockSignUp("New User","Test", username,"123");
        doLogIn(username, "123");

        // Switch to Credentials tab
        driver.findElement(By.id("nav-credentials-tab")).click();
    }

    @Test
    public void testAddCredential(){
        doBeforeTest("username_1");

        CredentialsTab credentialsTab = new CredentialsTab(driver);
        String credentialUrl = "Add New Url";
        String credentialUsername = "Add New Username";
        String credentialPassword = "AddNewPassword";
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Get row count of credential table before add new
        By tableRow = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr");
        int rowCountBeforeAdd = driver.findElements(tableRow).size();

        credentialsTab.addNewCredential(credentialUrl, credentialUsername, credentialPassword);

        // Check that the newly created credential is displayed on the credential table
        webDriverWait.until(ExpectedConditions.numberOfElementsToBe(tableRow, rowCountBeforeAdd + 1));
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr["+ (rowCountBeforeAdd + 1) +"]/th[1]")).getText().equals(credentialUrl));
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr["+ (rowCountBeforeAdd + 1) +"]/td[2]")).getText().equals(credentialUsername));
    }

    @Test
    public void testEditFirstCredential(){
        doBeforeTest("username_2");

        CredentialsTab credentialsTab = new CredentialsTab(driver);
        String credentialUrl = "Edit Url";
        String credentialUsername = "Edit Username";
        String credentialPassword = "EditPassword";

        credentialsTab.addNewCredential("Add New Url 1", "Add New Username 1", "AddNewPassword1");
        credentialsTab.addNewCredential("Add New Url 2", "Add New Username 2", "AddNewPassword2");

        credentialsTab.editFirstCredential(credentialUrl, credentialUsername, credentialPassword);

        // Check edited data
        By firstCredentialUrl = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr[1]/th[1]");
        By firstCredentialTUsername = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr[1]/td[2]");
        Assertions.assertTrue(driver.findElement(firstCredentialUrl).getText().equals(credentialUrl));
        Assertions.assertTrue(driver.findElement(firstCredentialTUsername).getText().equals(credentialUsername));
    }

    @Test
    public void testDeleteFirstCredential(){
        doBeforeTest("username_3");

        CredentialsTab credentialsTab = new CredentialsTab(driver);

        credentialsTab.addNewCredential("Add New Url 1", "Add New Username 1", "AddNewPassword1");
        credentialsTab.addNewCredential("Add New Url 2", "Add New Username 2", "AddNewPassword2");

        By tableRow = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr");
        int rowCountBefore = driver.findElements(tableRow).size();

        String deletedCredentialId = credentialsTab.deleteFirstCredential();

        int rowCountAfter = driver.findElements(tableRow).size();
        WebElement firstDeleteLink = credentialsTab.getFirstDeleteLink();
        String credentialIdOfFirstDeleteLink = credentialsTab.getCredentialIdOfDeleteLink(firstDeleteLink);

        // Check table row count
        Assertions.assertTrue(rowCountAfter == rowCountBefore - 1);
        // Check deleted CredentialId is not existing
        Assertions.assertFalse(credentialIdOfFirstDeleteLink.equals(deletedCredentialId));
    }
}
