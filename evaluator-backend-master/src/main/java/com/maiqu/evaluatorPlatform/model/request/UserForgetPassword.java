package com.maiqu.evaluatorPlatform.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserForgetPassword implements Serializable {
    private String userAccount;
    private String phone;
    private String email;
    private String newPassword;
    private String checkPassword;
}
