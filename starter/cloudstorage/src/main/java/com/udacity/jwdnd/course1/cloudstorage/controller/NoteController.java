package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/save-changes-note")
    public String saveChangesNote(@ModelAttribute("note") Note note, Authentication authentication, RedirectAttributes redirectAttributes){
        if(note.getNoteid() == null){
            note.setUserid(userService.getUser(authentication.getName()).getUserId());
            noteService.addNote(note);
            redirectAttributes.addFlashAttribute("addNoteSuccess", true);
        } else {
            noteService.editNote(note);
            redirectAttributes.addFlashAttribute("editNoteSuccess", true);
        }
        HomeController.activeTab = "Notes";
        return "redirect:/";
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, RedirectAttributes redirectAttributes) {
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("deleteNoteSuccess", true);
        HomeController.activeTab = "Notes";
        return "redirect:/";
    }
}
