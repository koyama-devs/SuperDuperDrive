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
        String url = "Add New Url";
        String username = "Add New Username";
        String password = "AddNewPassword";
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Get row count of credential table before add new
        By tableRow = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr");
        int rowCountBeforeAdd = driver.findElements(tableRow).size();

        credentialsTab.addNewCredential(url, username, password);

        // Check that the newly created credential is displayed on the credential table
        webDriverWait.until(ExpectedConditions.numberOfElementsToBe(tableRow, rowCountBeforeAdd + 1));
        WebElement newUrl = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr["+ (rowCountBeforeAdd + 1) +"]/th[1]"));
        WebElement newUsername = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr["+ (rowCountBeforeAdd + 1) +"]/td[2]"));
        Assertions.assertEquals(newUrl.getText(), url);
        Assertions.assertEquals(newUsername.getText(), username);
    }

    @Test
    public void testEditFirstCredential(){
        doBeforeTest("username_2");

        CredentialsTab credentialsTab = new CredentialsTab(driver);
        String url = "Edit Url";
        String username = "Edit Username";
        String password = "EditPassword";

        credentialsTab.addNewCredential("Add New Url 1", "Add New Username 1", "AddNewPassword1");
        credentialsTab.addNewCredential("Add New Url 2", "Add New Username 2", "AddNewPassword2");

        credentialsTab.editFirstCredential(url, username, password);

        // Check edited data
        By firstUrl = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr[1]/th[1]");
        By firstUsername = By.xpath("//*[@id=\"credentialTable\"]/tbody/tr[1]/td[2]");
        Assertions.assertEquals(driver.findElement(firstUrl).getText(), url);
        Assertions.assertEquals(driver.findElement(firstUsername).getText(), username);
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
        Assertions.assertEquals(rowCountAfter, rowCountBefore - 1);
        // Check deleted CredentialId is not existing
        Assertions.assertNotEquals(credentialIdOfFirstDeleteLink, deletedCredentialId);
    }
}
