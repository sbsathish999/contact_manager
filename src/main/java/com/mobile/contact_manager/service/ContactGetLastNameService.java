package com.mobile.contact_manager.service;

import com.mobile.contact_manager.model.Contact;

import java.util.List;

public interface ContactGetLastNameService {
    List<Contact> getByLastName(String name);
}
