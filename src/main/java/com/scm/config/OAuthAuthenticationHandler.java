package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.repositories.UserRepo;
import com.scm.util.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;

@Component
public class OAuthAuthenticationHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepo userRepo;

    Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthAuthenticationHandler");

        
        // identify provider
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        String provider = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(provider);
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        oAuth2User.getAttributes().forEach((key , value)->{
            logger.info(key + " : "+value);
        });
        
        User user1 = new User();
        user1.setEnabled(true);
        user1.setId(UUID.randomUUID().toString());
        user1.setEmailVerified(true);
        user1.setRoleList(List.of(AppConstants.ROLE_USER));



        // google
        if(provider.equalsIgnoreCase("google")){

            user1.setEmail(oAuth2User.getAttribute("email").toString());
            user1.setName(oAuth2User.getAttribute("name").toString());
            user1.setProfilePic(oAuth2User.getAttribute("picture").toString());
            user1.setProvider(Providers.GOOGLE);
            user1.setPassword("password");
             user1.setAbout("Account by Google");
             user1.setProviderId(oAuth2User.getName());
        }
        // github
        else if(provider.equalsIgnoreCase("github")){

           String email = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString():oAuth2User.getAttribute("login").toString() + "@gmail.com";
            String name = oAuth2User.getAttribute("name").toString();
           String picture = oAuth2User.getAttribute("avatar_url").toString();
           String providerUserId = oAuth2User.getName();

            user1.setEmail(email);
            user1.setName(name);
            user1.setProfilePic(picture);
            user1.setProvider(Providers.GITHUB);
            user1.setPassword("password");
            user1.setAbout("Account by Github");
            user1.setProviderId(providerUserId);
        }
       
        // create user and saving on the database
        
        User user2 = userRepo.findByEmail(user1.getEmail()).orElse(null);
        if(user2 == null){
            userRepo.save(user1);
            logger.info("\nUser1 saved successfully : "+user1.getEmail());
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
    

}
