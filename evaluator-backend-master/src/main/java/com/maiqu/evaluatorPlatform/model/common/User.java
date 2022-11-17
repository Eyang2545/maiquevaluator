package com.maiqu.evaluatorPlatform.model.common;

import lombok.Data;

/**
 * @author ht
 */
@Data
public class User extends Shared{
    /**
     * 用户昵称
     */
    protected String username;

    /**
     * 账户
     */
    protected String userAccount;

    /**
     * 电话
     */
    protected String phone;

    /**
     * 性别
     */
    protected Integer gender;


    /**
     * 性别
     */
    protected Integer age;

    /**
     * 用户密码
     */
    protected String userPassword;
}
