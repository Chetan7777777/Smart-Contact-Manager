package com.scm.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.service.UserService;
import com.scm.util.Message;
import com.scm.util.MessageType;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;






@Controller
public class PageController {

    @Autowired
    private UserService userService; 

    @GetMapping("/")
    public String getMethodName() {        
        return "index";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    @GetMapping("/services")
    public String services() {
        return "services";
    }
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
    @GetMapping("/login")
    public String signin() {
        return "login";
    }
    @PostMapping("/login")
    public String signinPost() {
        return "login";
    }
    
    
    
    @GetMapping("/signup")
    public String signup(Model model ) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm",userForm);        
        return "signup";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserForm userForm  , BindingResult bindingResult , HttpSession session) {
        if(bindingResult.hasErrors()){
            System.out.println("Error caught");
            return "signup";
        }
        
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .about(userForm.getPhoneNumber())
        // .profilePic("https://www.nicepng.com/png/full/73-730154_open-default-profile-picture-png.png")
        // .build();
        User user2 = new User();
        user2.setName(userForm.getName());
        user2.setEmail(userForm.getEmail());
        user2.setPassword(userForm.getPassword());
        user2.setAbout(userForm.getAbout());
        user2.setProfilePic("https://www.nicepng.com/png/full/73-730154_open-default-profile-picture-png.png");
        user2.setPhoneNumber(userForm.getPhoneNumber());
        

        userService.saveUser(user2);
        System.out.println("\n\n\n------\n user saved \n");
        
         Message message= Message.builder().content("Registraton Successfull!").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/signup";
    }
    
    
    
}
