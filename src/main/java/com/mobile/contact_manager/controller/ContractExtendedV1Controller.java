package com.mobile.contact_manager.controller;

import com.mobile.contact_manager.model.Contact;
import com.mobile.contact_manager.service.ContactMergeService;
import com.mobile.contact_manager.service.ContactService;
import com.mobile.contact_manager.service.impl.ExportAsJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact/v1")
public class ContractExtendedV1Controller {

    @Autowired
    ContactService contactService;

    @Autowired
    ContactMergeService contactMergeService;

    @PutMapping("/merge")
    public ResponseEntity mergeDuplicateContacts() {
        contactMergeService.mergeDuplicates();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/export")
    public ResponseEntity exportContacts(@RequestParam String exportType) {
        List<Contact> contactList = contactService.getAll();
        if(exportType.equalsIgnoreCase("json")){
            return new ExportAsJSON().exportContacts(contactList);
        }
       throw new RuntimeException("Invalid export type");
    }
}
