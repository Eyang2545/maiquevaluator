package com.maiqu.evaluatorPlatform.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账户
     */
    private String userAccount;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 0 是正常
     */
    private Integer userStatus;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0 -- 普通用户 1 -- 管理员
     */
    private Integer userRole;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    //官网搜逻辑删除
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 2L;
}
