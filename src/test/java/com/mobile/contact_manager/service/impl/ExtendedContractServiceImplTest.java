package com.mobile.contact_manager.service.impl;

import com.mobile.contact_manager.model.Contact;
import com.mobile.contact_manager.model.Name;
import com.mobile.contact_manager.model.Number;
import com.mobile.contact_manager.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExtendedContractServiceImplTest {

    @Mock
    private ContactRepository repository;

    @InjectMocks
    private ExtendedContractServiceImpl service;

    @Test
    void testMergeDuplicates() {
        Name name1 = new Name(1, "John", "Doe");
        Name name2 = new Name(2, "Jane", "Doe");
        Number number1 = new Number(1, "111-111-1111", "Mobile");
        Number number2 = new Number(2, "222-222-2222", "Home");
        Contact contact1 = new Contact(1, name1, Collections.singletonList(number1));
        Contact contact2 = new Contact(2, name1, Collections.singletonList(number2));
        Contact contact3 = new Contact(3, name2, Collections.singletonList(number1));
        Contact contact4 = new Contact(4, name2, Collections.singletonList(number2));

        List<Contact> contacts = Arrays.asList(contact1, contact2, contact3, contact4);
        when(repository.findAll()).thenReturn(contacts);
        service.mergeDuplicates();
        verify(repository, times(1)).deleteAll(anyList());
    }

    @Test
    void testFilterDuplicates() {
        Name name1 = new Name(1, "John", "Doe");
        Name name2 = new Name(2, "Jane", "Doe");
        Number number1 = new Number(1, "111-111-1111", "Mobile");
        Number number2 = new Number(2, "222-222-2222", "Home");
        Contact contact1 = new Contact(1, name1, Collections.singletonList(number1));
        Contact contact2 = new Contact(2, name1, Collections.singletonList(number2));
        Contact contact3 = new Contact(3, name2, Collections.singletonList(number1));
        Contact contact4 = new Contact(4, name2, Collections.singletonList(number2));

        List<Contact> contacts = Arrays.asList(contact1, contact2, contact3, contact4);

        List<Contact> duplicates = service.filterDuplicates(contacts);
        assertThat(duplicates).containsExactly(contact2, contact4);
    }

    @Test
    void testGetByFirstName() {
        // Mock data
        Name name1 = new Name(1, "John", "Doe");
        Name name2 = new Name(2, "Jane", "Doe");
        Number number1 = new Number(1, "111-111-1111", "Mobile", null);
        Number number2 = new Number(2, "222-222-2222", "Home", null);
        Contact contact1 = new Contact(1, name1, Collections.singletonList(number1));
        Contact contact2 = new Contact(2, name2, Collections.singletonList(number2));
        List<Contact> contacts = Arrays.asList(contact1, contact2);

        when(repository.findByNameFirstName("John")).thenReturn(Optional.of(Collections.singletonList(contact1)));

        List<Contact> result = service.getByFirstName("John");
        assertThat(result).containsExactly(contact1);
    }

    @Test
    void testGetByLastName_WhenContactExists_ReturnsContactList() {
        String lastName = "Doe";
        List<Contact> expectedContacts = new ArrayList<>();
        expectedContacts.add(new Contact(1, new Name(1, "John", "Doe"), null));
        expectedContacts.add(new Contact(2, new Name(2, "Jane", "Doe"), null));

        when(repository.findByNameLastName(lastName)).thenReturn(Optional.of(expectedContacts));
        List<Contact> actualContacts = service.getByLastName(lastName);
        assertEquals(expectedContacts, actualContacts);
    }

    @Test
    void testGetByLastName_WhenNoMatchingContactExists_ThrowsRuntimeException() {
        String lastName = "Smith";

        when(repository.findByNameLastName(lastName)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getByLastName(lastName));
    }

    @Test
    void testGetByFullName_WhenContactExists_ReturnsContactList() {
        String firstName = "John";
        String lastName = "Doe";
        List<Contact> expectedContacts = new ArrayList<>();
        expectedContacts.add(new Contact(1, new Name(1, "John", "Doe"), null));

        when(repository.findByNameFirstNameAndNameLastName(firstName, lastName)).thenReturn(Optional.of(expectedContacts));
        List<Contact> actualContacts = service.getByFullName(firstName, lastName);
        assertEquals(expectedContacts, actualContacts);
    }

    @Test
    void testGetByFullName_WhenNoMatchingContactExists_ThrowsRuntimeException() {
        String firstName = "John";
        String lastName = "Smith";

        when(repository.findByNameFirstNameAndNameLastName(firstName, lastName)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getByFullName(firstName, lastName));
    }
}
