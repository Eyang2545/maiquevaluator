package com.maiqu.evaluatorPlatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maiqu.evaluatorPlatform.model.User;
import com.maiqu.evaluatorPlatform.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService extends IService<User> {

    long userRegister(String userAccount,String userPassword,String checkPassword,String phone,String email);

    User doLoginBySession(String userAccount,String userPassword,HttpServletRequest request);

    String doLoginByToken(String userAccount,String password);

    User getSafetyUser(User originUser);

    int userLogOut(HttpServletRequest request);

    int updateUser(User user,User loginUser);

    boolean isAdmin(HttpServletRequest request);

    boolean isAdmin(User loginUser);

    User getLoginUser(HttpServletRequest request);

    User getLoginUserByToken(HttpServletRequest request);

    PageUtils queryPage(Map<String,Object> params);

    int logOutByToken(HttpServletRequest request);

    long updatePassword(String userAccount,String oldPassword,String newPassword,String checkPassword);

    long forgetPassword(String userAccount,String phone,String email,String newPassword,String checkPassword);

}
