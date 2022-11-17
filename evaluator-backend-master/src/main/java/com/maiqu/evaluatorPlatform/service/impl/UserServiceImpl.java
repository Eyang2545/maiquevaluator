package com.maiqu.evaluatorPlatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiqu.evaluatorPlatform.common.ErrorCode;
import com.maiqu.evaluatorPlatform.common.Query;
import com.maiqu.evaluatorPlatform.constant.UserConstant;
import com.maiqu.evaluatorPlatform.exception.BusinessException;
import com.maiqu.evaluatorPlatform.mapper.UserMapper;
import com.maiqu.evaluatorPlatform.model.User;
import com.maiqu.evaluatorPlatform.service.UserService;
import com.maiqu.evaluatorPlatform.utils.JwtUtil;
import com.maiqu.evaluatorPlatform.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final String SALT = "cfl";

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String phone, String email) {

        if (StringUtils.isAnyEmpty(userAccount, userPassword, checkPassword, phone, email)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "参数有的没填");
        }

        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号密码不能小于8位");
        }

        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        if (!userAccount.matches(regex)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "输入账号需要在6-18位且是数字和字母的组合且不能有特殊符号");
        }

        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "俩次输入密码不一致");
        }

        String md5HexPassword = DigestUtils.md5Hex(SALT + userPassword);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "已经有相同的注册账户");
        }

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(md5HexPassword);
        user.setPhone(phone);
        user.setEmail(email);
        boolean saveResult = this.save(user);

        if (!saveResult) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "没有成功保存");
        }

        return user.getId();
    }

    @Override
    public User doLoginBySession(String userAccount, String userPassword, HttpServletRequest request) {
        User safetyUser = checkLoginUserMessage(userAccount, userPassword);

        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public String doLoginByToken(String userAccount, String password) {
        User safetyUser = checkLoginUserMessage(userAccount, password);
        String tokenId = JwtUtil.generate(userAccount);
        redisTemplate.opsForValue().set(tokenId, safetyUser.getId(), 1, TimeUnit.DAYS);
        return tokenId;
    }

    private User checkLoginUserMessage(String userAccount, String userPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户长度小于4");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户密码小于8");
        }

        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        if (!userAccount.matches(regex)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "输入账号需要在6-18位且是数字和字母的组合且不能有特殊符号");
        }

        String md5HexPassword = DigestUtils.md5Hex(SALT + userPassword);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", md5HexPassword);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码不正确");
        }

        return getSafetyUser(user);

    }

    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "传入的原user为null");
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    @Override
    public int userLogOut(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 0;
    }

    @Override
    public int updateUser(User user, User loginUser) {
        if (user == null || loginUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = user.getId();
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!isAdmin(loginUser) && user.getId() != loginUser.getId()) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User oldUser = userMapper.selectById(id);
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return userMapper.updateById(user);
    }


    @Override
    public boolean isAdmin(HttpServletRequest request) {
        User userObj = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        return userObj != null && userObj.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "request为空");
        }
        Object loginUser = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        return (User) loginUser;
    }

    @Override
    public User getLoginUserByToken(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "request为空");
        }
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "请求头中没有携带token");
        }
        Object userId = redisTemplate.opsForValue().get(token);
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无法获得user对象");
        }
        User loginUser = userMapper.selectById((int) userId);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NO_AUTH, "未登录");
        }
        return getSafetyUser(loginUser);
    }

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        IPage<User> page = this.page(
                new Query<User>().getPage(params),
                new QueryWrapper<User>()
        );
        return new PageUtils(page);
    }

    @Override
    public int logOutByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisTemplate.delete(token);
        return 0;
    }

    @Override
    public long updatePassword(String userAccount, String oldPassword, String newPassword, String checkPassword) {
        if(StringUtils.isAnyBlank(userAccount,oldPassword,newPassword,checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数未填全");
        }
        if(!newPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"俩次输入的密码不一致");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        User user = userMapper.selectOne(queryWrapper);
        String md5HexPassword = DigestUtils.md5Hex(SALT + oldPassword);
        if(!user.getUserPassword().equals(md5HexPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"输入的用户名或者密码错误");
        }
        String newEncryptedPassword = DigestUtils.md5Hex(SALT + newPassword);
        user.setUserPassword(newEncryptedPassword);
        userMapper.updateById(user);
        return user.getId();
    }

    @Override
    public long forgetPassword(String userAccount, String phone, String email, String newPassword, String checkPassword) {
        if(StringUtils.isAnyBlank(userAccount,phone,email,newPassword,checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数未填全");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        User user = userMapper.selectOne(queryWrapper);
        if(!phone.equals(user.getPhone())||!email.equals(user.getEmail())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数有错误");
        }
        String md5HexPassword = DigestUtils.md5Hex(SALT + newPassword);
        user.setUserPassword(md5HexPassword);
        userMapper.updateById(user);
        return user.getId();
    }
}
