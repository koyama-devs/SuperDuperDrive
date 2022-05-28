package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public static String activeTab = "Files";
    private UserService userService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService, UserService userService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String listFiles(@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Model model, Authentication authentication) {
        User authorizedUser = userService.getUser(authentication.getName());
        if(authorizedUser == null){
            return "/login";
        }
        model.addAttribute("activeTab", activeTab);
        model.addAttribute("files", fileService.getAllFiles());
        model.addAttribute("notes", noteService.getAllNotes());
        model.addAttribute("credentialList", credentialService.getAllCredentials());
        return "home";
    }
}
