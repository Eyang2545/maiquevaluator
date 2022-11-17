package com.maiqu.evaluatorPlatform.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 2L;

    private String userAccount;

    private String userPassword;
}
