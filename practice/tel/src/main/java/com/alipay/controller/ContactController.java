package com.alipay.controller;

import com.alipay.model.Contact;
import com.alipay.model.Result;
import com.alipay.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<Contact> getContact(@RequestParam String name) {
        return Result.success();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> addContact(@RequestBody Contact contact) {
        return Result.success();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Result<?> editContact(@RequestBody Contact  contact) {
        return Result.success();
    }

}
