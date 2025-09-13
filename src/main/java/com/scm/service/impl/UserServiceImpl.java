package com.scm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.exception.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.service.UserService;
import com.scm.util.AppConstants;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void deletUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        userRepo.delete(user2);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> getUserById(String id) {
        User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        return Optional.ofNullable(user2);
    }

    @Override
    public boolean isUserExist(String id) {
        User user2 = userRepo.findById(id).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public User saveUser(User user) {
        // setting id of user
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        // we can set encoder and decoder for password also here
        System.out.println("\n\nPassword is : "+user.getPassword()+"\n\n");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        User savedUser = userRepo.save(user);

        return savedUser;
    }

    @Override
    public Optional<User> updatUser(User user) {
        
        User user2 = userRepo.findById(user.getId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setProvider(user.getProvider());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setSocialLinks(user.getSocialLinks());
        user2.setProviderId(user.getProviderId());
        user2.setProvider(user.getProvider());
        user2.setAbout(user.getAbout());
        user2.setEnabled(user.isEnabled());

        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
    

}
