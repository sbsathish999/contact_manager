package com.mobile.contact_manager.service;

import com.mobile.contact_manager.model.Contact;

import java.util.List;

public interface ContactGetFirstNameService {

    List<Contact> getByFirstName(String firstName);
}
