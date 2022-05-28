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

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/save-changes-note")
    public String saveChangesNote(@ModelAttribute("note") Note note, Authentication authentication){
        if(note.getNoteid() == null){
            note.setUserid(userService.getUser(authentication.getName()).getUserId());
            noteService.addNote(note);
        } else {
            noteService.editNote(note);
        }
        HomeController.activeTab = "Notes";
        return "redirect:/";
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable Integer noteId) {
        noteService.deleteNote(noteId);
        HomeController.activeTab = "Notes";
        return "redirect:/";
    }
}
