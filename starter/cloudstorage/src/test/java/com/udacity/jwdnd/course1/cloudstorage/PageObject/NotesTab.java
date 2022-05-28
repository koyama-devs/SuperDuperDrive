package com.udacity.jwdnd.course1.cloudstorage.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// page_url = about:blank
public class NotesTab {
    private WebDriver driver;

    public NotesTab(WebDriver driver) {
        this.driver = driver;
    }

    // The Notes tab contains several HTML elements that will be represented as WebElements.
    private By addANewNoteButton = By.id("add-note-button");

    private By firstEditButton = By.xpath("//*[@id=\"noteTable\"]/tbody/tr[1]/td[1]/button");

    private By firstDeleteLink = By.xpath("//*[@id=\"noteTable\"]/tbody/tr[1]/td[1]/a");
    private By titleTextField = By.id("note-title");
    private By descriptionTextField = By.id("note-description");
    private By saveChangesButton = By.id("note-save-changes-button");

    private void fillNoteModalAndSaveChanges(String title, String description) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Fill Note Title
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(titleTextField));
        WebElement inputTitle = driver.findElement(titleTextField);
        inputTitle.click();
        inputTitle.clear();
        inputTitle.sendKeys(title);

        // Fill Note Description
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(descriptionTextField));
        WebElement inputDescription = driver.findElement(descriptionTextField);
        inputDescription.click();
        inputDescription.clear();
        inputDescription.sendKeys(description);

        // Click "Save changes"
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(saveChangesButton));
        WebElement buttonSaveChanges = driver.findElement(saveChangesButton);
        buttonSaveChanges.click();
    }

    // The Notes tab allows the user to add a new note
    public NotesTab addNewNote(String title, String description) {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click "+ Add a New Note" button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(addANewNoteButton));
        WebElement addNoteButton = driver.findElement(addANewNoteButton);
        addNoteButton.click();

        fillNoteModalAndSaveChanges(title, description);

        return this;
    }

    public NotesTab editFirstNote(String title, String description) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click "Edit" button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(firstEditButton));
        WebElement editBtn = driver.findElement(firstEditButton);
        editBtn.click();

        fillNoteModalAndSaveChanges(title, description);

        return this;
    }

    public String deleteFirstNote() {
        // Click the first "Delete" button
        WebElement deleteLink = getFirstDeleteLink();
        String noteId = getNoteIdOfDeleteLink(deleteLink);
        deleteLink.click();

        return noteId;
    }

    public WebElement getFirstDeleteLink() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Click the first "Delete" button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(firstDeleteLink));
        WebElement deleteLink = driver.findElement(firstDeleteLink);

        return deleteLink;
    }

    public String getNoteIdOfDeleteLink(WebElement deleteLink) {
        String href = deleteLink.getAttribute("href");
        return href.substring(href.lastIndexOf('/') + 1);
    }
}