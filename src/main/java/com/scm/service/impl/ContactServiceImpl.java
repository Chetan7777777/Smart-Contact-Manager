package com.scm.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.exception.ResourceNotFoundException;

import com.scm.repositories.ContactRepo;
import com.scm.service.ContactService;

import lombok.experimental.var;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        // TODO Auto-generated method stub
       String contactId = UUID.randomUUID().toString();
       contact.setId(contactId);
       return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Contact> getAll() {
        // TODO Auto-generated method stub
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        // TODO Auto-generated method stub
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact Not Found"));
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        Contact contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact Not Found"));
        contactRepo.delete(contact);
        
    }

    @Override
    public List<Contact> getByUserId(String userId) {
     return null;
    }

    @Override
    public Page<Contact> getByUser(User user , int page , int size , String sortBy , String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page , size , sort);
        return contactRepo.findByUser(user , pageable);
    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByNameContaining(nameKeyword,pageable); 
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order) {
                Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByNameContaining(emailKeyword,pageable); 
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order) {
                Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByNameContaining(phoneNumberKeyword,pageable); 
    }



}
