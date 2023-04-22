package com.mobile.contact_manager.service.impl;

import com.mobile.contact_manager.model.Contact;
import com.mobile.contact_manager.model.Name;
import com.mobile.contact_manager.repository.ContactRepository;
import com.mobile.contact_manager.service.ContactGetFirstNameService;
import com.mobile.contact_manager.service.ContactGetFullNameService;
import com.mobile.contact_manager.service.ContactGetLastNameService;
import com.mobile.contact_manager.service.ContactMergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExtendedContractServiceImpl implements
        ContactMergeService, ContactGetFirstNameService, ContactGetLastNameService, ContactGetFullNameService {

    @Autowired
    ContactRepository repository;

    @Override
    public void mergeDuplicates() {
        List<Contact> list = repository.findAll();
        List<Contact> removalContacts = filterDuplicates(list);
        removalContacts.forEach(System.out :: println);
        repository.deleteAll(removalContacts);
    }

    public List<Contact> filterDuplicates(List<Contact> contactList) {
        return contactList.stream()
                .collect(Collectors.groupingBy(
                        contact -> contact.getName().getFullName().trim().toLowerCase()))
                .values().stream()
                .filter(list -> list.size() > 1)
                .flatMap(list -> list.stream().skip(1))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> getByFirstName(String firstName) {
        return repository.findByNameFirstName(firstName)
                .orElseThrow(() -> new RuntimeException("No matching contacts found"));
    }

    @Override
    public List<Contact> getByLastName(String name) {
        return repository.findByNameLastName(name)
                .orElseThrow(() -> new RuntimeException("No matching contacts found"));
    }

    @Override
    public List<Contact> getByFullName(String firstName, String lastName) {
        return repository.findByNameFirstNameAndNameLastName(firstName, lastName)
                .orElseThrow(() -> new RuntimeException("No matching contacts found"));
    }
}
