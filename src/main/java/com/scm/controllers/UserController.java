package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.service.UserService;
import com.scm.service.impl.UserServiceImpl;
import com.scm.util.Helper;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping(value = "/user" , method = {RequestMethod.GET,RequestMethod.POST})
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class); 

    //  user Dashboard page
    @GetMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    // user profile page

    @GetMapping("/profile")
    public String profile( Model model , Authentication authentication) {
        


        return "user/profile";
    }

    // user add contacts page

    // user view contacts

    // user edit contact

    // user edit contact

    // user delete contact

    // user search contact

}
