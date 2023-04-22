package com.mobile.contact_manager.service;

import com.mobile.contact_manager.model.Contact;

import java.util.List;

public interface ContactGetFullNameService {
    List<Contact> getByFullName(String firstName, String lastName);
}
