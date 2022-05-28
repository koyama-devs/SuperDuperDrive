package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.PageObject.NotesTab;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddEditDeleteNoteTests extends BaseTests{

//    private NotesTab notesTab;
//
//    public AddEditDeleteNotesTests(NotesTab notesTab) {
//        this.notesTab = notesTab;
//    }
//
//    @BeforeAll
//    static void beforeAll() {
//
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        notesTab = new NotesTab(driver);
//    }

    private void doBeforeTest(String username) {
        // Create a test account
        doMockSignUp("New User","Test", username,"123");
        doLogIn(username, "123");

        // Switch to Notes tab
        driver.findElement(By.id("nav-notes-tab")).click();
    }

    @Test
    public void testAddNote(){
        doBeforeTest("username1");

        NotesTab notesTab = new NotesTab(driver);
        String noteTitle = "Add New Title";
        String noteDescription = "Add New Description";
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        // Get row count of note table before add new
        By tableRow = By.xpath("//*[@id=\"noteTable\"]/tbody/tr");
        int rowCountBeforeAdd = driver.findElements(tableRow).size();

        notesTab.addNewNote(noteTitle, noteDescription);

        // Check that the newly created note is displayed on the note table
        webDriverWait.until(ExpectedConditions.numberOfElementsToBe(tableRow, rowCountBeforeAdd + 1));
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@id=\"noteTable\"]/tbody/tr["+ (rowCountBeforeAdd + 1) +"]/th[1]")).getText().equals(noteTitle));
        Assertions.assertTrue(driver.findElement(By.xpath("//*[@id=\"noteTable\"]/tbody/tr["+ (rowCountBeforeAdd + 1) +"]/td[2]")).getText().equals(noteDescription));
    }

    @Test
    public void testEditFirstNote(){
        doBeforeTest("username2");

        NotesTab notesTab = new NotesTab(driver);
        String noteTitle = "Edit Title";
        String noteDescription = "Edit Description";

        notesTab.addNewNote("Add New Title 1", "Add New Description 1");
        notesTab.addNewNote("Add New Title 2", "Add New Description 2");

        notesTab.editFirstNote(noteTitle, noteDescription);

        // Check edited data
        By firstNoteTitle = By.xpath("//*[@id=\"noteTable\"]/tbody/tr[1]/th[1]");
        By firstNoteTDescription = By.xpath("//*[@id=\"noteTable\"]/tbody/tr[1]/td[2]");
        Assertions.assertTrue(driver.findElement(firstNoteTitle).getText().equals(noteTitle));
        Assertions.assertTrue(driver.findElement(firstNoteTDescription).getText().equals(noteDescription));
    }

    @Test
    public void testDeleteFirstNote(){
        doBeforeTest("username3");

        NotesTab notesTab = new NotesTab(driver);

        notesTab.addNewNote("Add New Title 1", "Add New Description 1");
        notesTab.addNewNote("Add New Title 2", "Add New Description 2");

        By tableRow = By.xpath("//*[@id=\"noteTable\"]/tbody/tr");
        int rowCountBefore = driver.findElements(tableRow).size();

        String deletedNoteId = notesTab.deleteFirstNote();

        int rowCountAfter = driver.findElements(tableRow).size();
        WebElement firstDeleteLink = notesTab.getFirstDeleteLink();
        String noteIdOfFirstDeleteLink = notesTab.getNoteIdOfDeleteLink(firstDeleteLink);

        // Check table row count
        Assertions.assertTrue(rowCountAfter == rowCountBefore - 1);
        // Check deleted NoteId is not existing
        Assertions.assertFalse(noteIdOfFirstDeleteLink.equals(deletedNoteId));
    }
}
