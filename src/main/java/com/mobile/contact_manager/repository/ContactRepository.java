package com.mobile.contact_manager.repository;

import com.mobile.contact_manager.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Optional<List<Contact>> findByNameFirstName(String firstName);

    Optional<List<Contact>> findByNameLastName(String name);

    Optional<List<Contact>> findByNameFirstNameAndNameLastName(String firstName, String lastName);
}
