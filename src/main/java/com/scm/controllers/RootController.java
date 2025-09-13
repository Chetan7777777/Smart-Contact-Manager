package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.service.UserService;
import com.scm.util.Helper;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;
    @ModelAttribute
    public void addLogggedInUserInformation(Model model , Authentication authentication){
        if(authentication == null) return;

        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        model.addAttribute("loggedUser", user);

            
    }

}
