package com.alipay.repository;

import com.alipay.model.Contact;

import java.util.List;

public interface ContactRepository {
    void insert(Contact contact);

    void update(Contact contact);

    Contact getById(Long id);

    void delete(Long id);

    List<Contact> queryAll();
}
