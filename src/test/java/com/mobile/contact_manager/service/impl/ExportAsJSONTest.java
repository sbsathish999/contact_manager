package com.mobile.contact_manager.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.contact_manager.model.Contact;
import com.mobile.contact_manager.model.Name;
import com.mobile.contact_manager.model.Number;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExportAsJSONTest {

    @Test
    void testExportContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(new Contact(1, new Name(1, "John", "Doe"), Arrays.asList(new Number(1, "9500557522", "Mobile"))));
        contacts.add(new Contact(2, new Name(2, "Jane", "Doe"), Arrays.asList(new Number(2, "8500557522", "Work"))));

        ExportAsJSON exportService = new ExportAsJSON();
        ResponseEntity responseEntity = exportService.exportContacts(contacts);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        //assertEquals("[attachment; filename=data.json]", responseEntity.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION));

        Resource resource = (Resource) responseEntity.getBody();
        assertNotNull(resource);

        try {
            String json = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            List<Map<String, Object>> list = new ObjectMapper().readValue(json, List.class);
            assertTrue(list.get(0).get("id").equals(1));
            assertTrue(list.get(1).get("id").equals(2));
        } catch (Exception e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
    }
}
