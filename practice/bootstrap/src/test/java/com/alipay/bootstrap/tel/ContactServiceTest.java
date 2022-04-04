package com.alipay.bootstrap.tel;

import com.alipay.bootstrap.BootstrapApplicationTests;
import com.alipay.model.Contact;
import com.alipay.repository.ContactRepository;
import com.alipay.service.ContactService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ContactServiceTest extends BootstrapApplicationTests {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    @ParameterizedTest
    @MethodSource(value = "contactFactory")
    public void addContactTest(Contact contact) {
        contactService.addContact(contact);
        final Long id = contact.getId();
        Contact contact1 = contactRepository.getById(id);
        Assertions.assertEquals(contact1.getName(), "abc");
    }

    @ParameterizedTest
    @MethodSource(value = "contactFactory")
    public void deleteContactTest(Contact contact) {
        contactRepository.insert(contact);
        final Long id = contact.getId();
        contactService.deleteContact(id);
        Contact contact1 = contactRepository.getById(id);
        Assertions.assertNull(contact1);
    }

    @ParameterizedTest
    @MethodSource(value = "contactFactory")
    public void queryContactTest(Contact contact) {
        contactRepository.insert(contact);
        String s = "ab";
        List<Contact> contactList = contactService.queryContact(s);
        for (Contact contact1 : contactList) {
            Assertions.assertTrue(contact1.getName().contains(s));
        }
    }

    @ParameterizedTest
    @MethodSource(value="contactFactory")
    public void getMobileByNameTest(Contact contact) {
        contactRepository.insert(contact);
        final Long id = contact.getId();
        Contact contact1 = contactRepository.getById(id);
        Assertions.assertEquals(contact1.getMobile(),"123");
    }

    @ParameterizedTest
    @MethodSource(value="contactFactory")
    public void getByIdTest(Contact contact) {
        contactRepository.insert(contact);
        final Long id = contact.getId();
        Contact contact1 = contactRepository.getById(id);
        Assertions.assertEquals(contact1.getId(),id);
    }

    @ParameterizedTest
    @MethodSource(value="contactFactory")
    public void updateContactTest(Contact contact) {
        contactRepository.insert(contact);
        final Long id = contact.getId();
        Contact contact1 = new Contact();
        contact1.setQq("a");
        contactService.updateContact(contact1);
        Assertions.assertNotSame(contact,contact1);
    }


    private static Stream<Contact> contactFactory() {
        Contact contact = new Contact();
        long id = new Random().nextLong();
        contact.setId(id);
        contact.setName("abc");
        contact.setQq("1234");
        contact.setEmail("qq.com");
        contact.setMobile("123");
        contact.setMemo("12");
        return Stream.of(contact);
    }
}
