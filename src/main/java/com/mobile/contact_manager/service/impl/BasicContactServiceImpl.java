package com.mobile.contact_manager.service.impl;

import com.mobile.contact_manager.model.Contact;
import com.mobile.contact_manager.repository.ContactRepository;
import com.mobile.contact_manager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository repository;
    @Override
    public Contact get(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Invalid contact Id"));
    }

    @Override
    public List<Contact> getAll() {
        return repository.findAll();
    }

    @Override
    public Contact add(Contact contact) {
        return repository.save(contact);
    }

    @Override
    public Boolean delete(Integer id) {
        Contact contact = get(id);
        repository.delete(contact);
        return true;
    }
}
