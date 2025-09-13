package com.scm.controllers;

import java.util.List;

import org.hibernate.boot.model.source.internal.hbm.ModelBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.service.ImageService;
import com.scm.service.impl.ContactServiceImpl;
import com.scm.service.impl.UserServiceImpl;
import com.scm.util.AppConstants;
import com.scm.util.Helper;
import com.scm.util.Message;
import com.scm.util.MessageType;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    
    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactServiceImpl contactServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ImageService imageService;

    @RequestMapping("/add")
    public String addContactView(Model model){
       ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm",contactForm);

        return "user/add_contact";
    }
    @PostMapping("/process-contact")
    public String postMethodName(@Valid @ModelAttribute ContactForm contactForm , BindingResult result , Authentication authentication , HttpSession session) {
        
        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> logger.info("error --> "+error.toString()));
            Message message = new Message();
            message.setContent("Please correct the following errors!");
            message.setType(MessageType.red);
            session.setAttribute("message", message);
            return "user/add_contact";
        }
        
        String fileURL = imageService.uploadImage(contactForm.getContactImage());
        Contact contact = new Contact();

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = (User) userServiceImpl.getUserByEmail(username);

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNo());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setFavorite(contactForm.isFavorite());
        contact.setUser(user);
        contact.setPicture(fileURL);
        logger.info("file url -> {}",fileURL);


        contactServiceImpl.save(contact);

            Message message = new Message();
            message.setContent("Contact saved successfully!");
            message.setType(MessageType.green);
            session.setAttribute("message", message);
        return "user/add_contact";
    }
    @GetMapping("/contacts")
    public String viewContacts(
    @RequestParam(value =  "page" , defaultValue = "0") int page ,
    @RequestParam(value = "size" , defaultValue = "4") int size ,
    @RequestParam(value = "sortBy" , defaultValue = "name") String sortBy ,
    @RequestParam(value = "direction" , defaultValue="asc") String direction ,

    Model model, Authentication authentication) {

        String email = Helper.getEmailOfLoggedInUser(authentication);

        User user = userServiceImpl.getUserByEmail(email); 

        Page<Contact> pageContact = contactServiceImpl.getByUser(user , page , size , sortBy , direction);
        
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("pageContact", pageContact);
        return "user/contacts";
    }
    @GetMapping("/search")
    public String searchHandler(
        @RequestParam("field") String field,
        @RequestParam("keyword") String value,
        @RequestParam(value="size",defaultValue = "4") int size,
        @RequestParam(value="page",defaultValue = "0") int page,
        @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value="direction",defaultValue = "asc") String direction , Model model
    ){
        logger.info("field : {} , keyword {}",field,value);

        Page<Contact>pageContact = null;
        if(field.equalsIgnoreCase("name")){
            pageContact = contactServiceImpl.searchByName(value,size,page,sortBy,direction);
        } else if(field.equalsIgnoreCase("email")){
            pageContact = contactServiceImpl.searchByEmail(value, size, page, sortBy, direction);
        } else if(field.equalsIgnoreCase("phoneNumber")){
            pageContact = contactServiceImpl.searchByEmail(value, size, page, sortBy, direction);
        } 
        logger.info("pagecontact : {}",pageContact);
        
        model.addAttribute("pageContact", pageContact);

        return "user/search";
    }
    

}
