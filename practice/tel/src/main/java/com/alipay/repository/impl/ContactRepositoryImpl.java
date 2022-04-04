package com.alipay.repository.impl;

import com.alipay.model.Contact;
import com.alipay.model.ContactTypeEnum;
import com.alipay.repository.ContactRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactRepositoryImpl implements ContactRepository {
    private static final String FILEPATH = "contact.txt";
    private List<Contact> contacts = new ArrayList<>();

    @Override
    public void insert(Contact contact) {
        contacts.add(contact);
    }

    @Override
    public void update(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(contact.getId())) {
                contacts.set(i, contact);
                break;
            }
        }
    }

    @Override
    public Contact getById(Long id) {
        for (Contact contact : contacts) {
            if (contact.getId().equals(id)) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("no such id");
        }
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(id)) {
                contacts.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Contact> queryAll() {
        return contacts;
    }


    @PostConstruct
    public void init() throws IOException {
        InputStream inputStream = ContactRepositoryImpl.class.getClassLoader().getResourceAsStream(FILEPATH);
        if (inputStream == null) {
            throw new RuntimeException();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] elements = line.split(" ");
            Contact contact = new Contact();
            contact.setId(Long.parseLong(elements[0]));
            contact.setName(elements[1]);
            contact.setMobile(elements[2]);
            contact.setType(ContactTypeEnum.toType(elements[3]));
            contact.setEmail(elements[4]);
            contact.setQq(elements[5]);
            contact.setMemo(elements[6]);
            contacts.add(contact);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try (FileOutputStream fileOutputStream = new FileOutputStream(FILEPATH)) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                PrintWriter printWriter = new PrintWriter(outputStreamWriter);
                for (Contact contact : contacts) {
                    String stringBuilder = contact.getId() + " " +
                            contact.getName() + " " +
                            contact.getMobile() + " " +
                            contact.getType() + " " +
                            contact.getEmail() + " " +
                            contact.getQq() + " " +
                            contact.getMemo();
                    printWriter.println(stringBuilder);
                }
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
