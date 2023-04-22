package com.mobile.contact_manager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.contact_manager.model.Contact;
import com.mobile.contact_manager.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ExportAsJSON implements ExportService {

    @Override
    public ResponseEntity exportContacts(List<Contact> contactList) {
        String jsonString = null;
        try {
            jsonString = new ObjectMapper().writeValueAsString(contactList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(jsonString.getBytes());

        Resource resource = new InputStreamResource(bis);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }
}
