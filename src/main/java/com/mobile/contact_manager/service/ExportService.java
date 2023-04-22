package com.mobile.contact_manager.service;

import com.mobile.contact_manager.model.Contact;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExportService {

    ResponseEntity exportContacts(List<Contact> contactList);
}
