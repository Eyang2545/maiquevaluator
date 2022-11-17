package com.maiqu.evaluatorPlatform.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {

    /**
     * 用户id
     */
    protected long id;

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
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;
}
