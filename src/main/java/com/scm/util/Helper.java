package com.scm.util;

import java.security.Principal;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    private Logger logger = LoggerFactory.getLogger(Helper.class);

    public static String getEmailOfLoggedInUser(Authentication authentication){

        if(authentication instanceof OAuth2AuthenticationToken){
            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String userEmail = "";
            // Getting email by GOOGLE
            if(clientId.equalsIgnoreCase("google")){
                System.out.println("\nGetting email from google \n");
                userEmail = oauth2User.getAttribute("email").toString();
            }
            // Getting email by GITHUB
            else if(clientId.equalsIgnoreCase("github")){
                System.out.println("\nGetting email from github \n");
                userEmail = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString():oauth2User.getAttribute("login").toString() + "@gmail.com";

            } 
            return userEmail;
        }
        // Getting email by Normal signin in 
        else{
                System.out.println("\nGetting email from normal user \n");
                return authentication.getName();
            }
    }
}
