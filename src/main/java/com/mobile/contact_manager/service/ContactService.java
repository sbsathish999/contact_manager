package com.mobile.contact_manager.service;

import com.mobile.contact_manager.model.Contact;

import java.util.List;

public interface ContactService {

    Contact get(Integer id);

    List<Contact> getAll();

    Contact add(Contact contact);

    Boolean delete(Integer id);

}
