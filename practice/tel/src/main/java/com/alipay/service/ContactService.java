package com.alipay.service;

import com.alipay.model.Contact;

import java.util.List;

public interface ContactService {
    void addContact(Contact contact);

    void updateContact(Contact contact);

    String getMobileByName(String name);

    void deleteContact(Long id);

    Contact getById(Long id);

    /**
     * 模糊查询
     *
     * @param s 查询关键字
     * @return 匹配结果
     */
    List<Contact> queryContact(String s);


}
