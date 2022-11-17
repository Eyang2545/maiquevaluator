package com.maiqu.evaluatorPlatform.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdatePasswordRequest implements Serializable {
    private static final long serialVersionUID = 2L;
    String userAccount;
    String oldPassword;
    String newPassword;
    String checkPassword;
}
