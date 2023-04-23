package com.mobile.contact_manager.service.impl;

import com.mobile.contact_manager.model.Contact;
import com.mobile.contact_manager.model.Name;
import com.mobile.contact_manager.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasicContactServiceImplTest {

    @InjectMocks
    BasicContactServiceImpl contactService;

    @Mock
    ContactRepository repository;

    @Test
    public void testGetContactById() {
        Contact contact = new Contact(1, new Name(1, "John", "Doe"), new ArrayList<>());
        when(repository.findById(1)).thenReturn(Optional.of(contact));
        Contact actualContact = contactService.get(1);
        assertEquals(contact.getId(), actualContact.getId());
        assertEquals(contact.getName().getFullName(), actualContact.getName().getFullName());
        assertEquals(contact.getNumbers().size(), actualContact.getNumbers().size());
    }

    @Test
    public void testGetContactByIdInvalidId() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            contactService.get(1);
        });
    }

    @Test
    public void testGetAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        Contact contact1 = new Contact(1, new Name(1, "John", "Doe"), new ArrayList<>());
        Contact contact2 = new Contact(2, new Name(2, "Jane", "Doe"), new ArrayList<>());
        contacts.add(contact1);
        contacts.add(contact2);
        when(repository.findAll()).thenReturn(contacts);
        List<Contact> actualContacts = contactService.getAll();
        assertEquals(contacts.size(), actualContacts.size());
        assertEquals(contacts.get(0).getId(), actualContacts.get(0).getId());
        assertEquals(contacts.get(0).getName().getFullName(), actualContacts.get(0).getName().getFullName());
        assertEquals(contacts.get(0).getNumbers().size(), actualContacts.get(0).getNumbers().size());
        assertEquals(contacts.get(1).getId(), actualContacts.get(1).getId());
        assertEquals(contacts.get(1).getName().getFullName(), actualContacts.get(1).getName().getFullName());
        assertEquals(contacts.get(1).getNumbers().size(), actualContacts.get(1).getNumbers().size());
    }

    @Test
    public void testAddContact() {
        Contact contact = new Contact(1, new Name(1, "John", "Doe"), new ArrayList<>());
        when(repository.save(contact)).thenReturn(contact);
        Contact actualContact = contactService.add(contact);
        assertEquals(contact.getId(), actualContact.getId());
        assertEquals(contact.getName().getFullName(), actualContact.getName().getFullName());
        assertEquals(contact.getNumbers().size(), actualContact.getNumbers().size());
    }

    @Test
    public void testDeleteContact() {
        Contact contact = new Contact(1, new Name(1, "John", "Doe"), new ArrayList<>());
        when(repository.findById(1)).thenReturn(Optional.of(contact));
        Boolean isDeleted = contactService.delete(1);
        assertTrue(isDeleted);
        verify(repository, times(1)).delete(contact);
    }

    @Test
    void testDeleteContactInvalidId() {
        Integer invalidId = 1;
        doThrow(new RuntimeException("Invalid contact Id")).when(repository).findById(invalidId);

        assertThrows(RuntimeException.class, () -> {
            contactService.delete(invalidId);
        });

        verify(repository, times(1)).findById(invalidId);
        verifyNoMoreInteractions(repository);
    }
}
