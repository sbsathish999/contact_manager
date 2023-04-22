package com.mobile.contact_manager.controller;

import com.mobile.contact_manager.model.Contact;
import com.mobile.contact_manager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/{contactId}")
    public Contact getContactById(@PathVariable Integer contactId) {
        return contactService.get(contactId);
    }

    @PostMapping
    public Contact saveContact(@RequestBody Contact contact) {
        return contactService.add(contact);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity saveContact(@PathVariable Integer contactId) {
        contactService.delete(contactId);
        return ResponseEntity.ok().build();
    }
}
