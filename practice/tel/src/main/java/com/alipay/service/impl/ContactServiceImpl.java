package com.alipay.service.impl;

import com.alipay.model.Contact;
import com.alipay.repository.ContactRepository;
import com.alipay.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void addContact(Contact contact) {
        contactRepository.insert(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        contactRepository.update(contact);
    }

    @Override
    public String getMobileByName(String name) {
        return null;
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.delete(id);
    }

    @Override
    public Contact getById(Long id) {
        return contactRepository.getById(id);
    }

    @Override
    public List<Contact> queryContact(String s) {
        List<Contact> contacts = contactRepository.queryAll();
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().contains(s)) {
                result.add(contact);
            }
        }
        return result;
    }
}
