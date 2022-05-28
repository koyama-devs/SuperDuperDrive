package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/save-changes-credential")
    public String saveChangesNote(@ModelAttribute("credential") Credential credential, Authentication authentication, RedirectAttributes redirectAttributes){
        if(credential.getCredentialid() == null){
            credential.setUserid(userService.getUser(authentication.getName()).getUserId());
            credentialService.addCredential(credential);
            redirectAttributes.addFlashAttribute("addCredentialSuccess", true);
        } else {
            credentialService.editCredential(credential);
            redirectAttributes.addFlashAttribute("editCredentialSuccess", true);
        }
        HomeController.activeTab = "Credentials";
        return "redirect:/";
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteNote(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes) {
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("deleteCredentialSuccess", true);
        HomeController.activeTab = "Credentials";
        return "redirect:/";
    }
}
