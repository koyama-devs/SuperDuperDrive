package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

//    @GetMapping("/credentials")
//    public String listUploadedFiles(@ModelAttribute("note") Note noteModal, Model model) {
//        model.addAttribute("activeTab", "Credentials");
//        model.addAttribute("credentialList", credentialService.getAllCredentials());
//        return "home";
//    }

    @PostMapping("/save-changes-credential")
    public String saveChangesNote(@ModelAttribute("credential") Credential credential, Model model, Authentication authentication){
        if(credential.getCredentialid() == null){
            credential.setUserid(userService.getUser(authentication.getName()).getUserId());
            credentialService.addCredential(credential);
        } else {
            credentialService.editCredential(credential);
        }
        HomeController.activeTab = "Credentials";
//        redirectAttributes.addFlashAttribute("errorMessageList", noteService.getAllErrors());
        return "redirect:/";
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteNote(@PathVariable Integer credentialId) {
        credentialService.deleteCredential(credentialId);
        HomeController.activeTab = "Credentials";
        return "redirect:/";
    }
}
