package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping()
    public String signup(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){
        String signupErrorMessage = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupErrorMessage = "The username already exists.";
        }

        if (signupErrorMessage == null) {
            int userInserted = userService.createUser(user);
            if (userInserted < 0) {
                signupErrorMessage = "There was an error signing you up. Please try again.";
            }
        }

        if (signupErrorMessage == null) {
            model.addAttribute("signupSuccess", true);
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/login";
        } else {
            model.addAttribute("signupError", signupErrorMessage);
        }
        return "signup";
    }
}
