package com.scm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

import java.util.List;


@Repository
public interface ContactRepo extends JpaRepository<Contact , String>{
    // find contacts by user
    Page<Contact> findByUser(User user , Pageable pageable);

        // @Query("Select c from Contact where c.userid =:userId")
    // List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByNameContaining(String nameKeyword , Pageable pageable);
    Page<Contact> findByEmailContaining(String emailKeyword , Pageable pageable);
    Page<Contact> findByPhoneNumberContaining(String phoneNumberKeyword , Pageable pageable);

}
