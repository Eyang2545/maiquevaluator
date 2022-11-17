package com.maiqu.evaluatorPlatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maiqu.evaluatorPlatform.common.ErrorCode;
import com.maiqu.evaluatorPlatform.exception.BusinessException;
import com.maiqu.evaluatorPlatform.mapper.EvaluatorMapper;
import com.maiqu.evaluatorPlatform.model.User;
import com.maiqu.evaluatorPlatform.model.entity.Evaluator;
import com.maiqu.evaluatorPlatform.model.vo.EvaluatorVO;
import com.maiqu.evaluatorPlatform.model.vo.UserVO;
import com.maiqu.evaluatorPlatform.service.EvaluatorService;
import com.maiqu.evaluatorPlatform.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author ht
 */

@Service
@Slf4j
public class EvaluatorServiceImpl extends ServiceImpl<EvaluatorMapper, Evaluator> implements EvaluatorService {


    private static final String SALT = "maiquceping";


    @Resource
    private EvaluatorMapper evaluatorMapper;



    @Resource
    private RedisTemplate redisTemplate;



    @Override
    public String doLoginByToken(String userAccount, String userPassword) {
        EvaluatorVO evaluatorVO = checkLoginUserMessage(userAccount, userPassword);
        String tokenId = String.valueOf(UUID.randomUUID());
        redisTemplate.opsForValue().set(tokenId, evaluatorVO.getId(), 1, TimeUnit.DAYS);
        return tokenId;
    }

    @Override
    public EvaluatorVO getLoginUserByToken(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "request为空");
        }
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "请求头中没有携带token");
        }
        Object evaluatorId = redisTemplate.opsForValue().get(token);
        if (evaluatorId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "无法获得测评师对象");
        }
        Evaluator evaluator = evaluatorMapper.selectById((int) evaluatorId);
        if (evaluator == null) {
            throw new BusinessException(ErrorCode.NO_AUTH, "未登录");
        }
        return evaluator.toVo();
    }

    private EvaluatorVO checkLoginUserMessage(String userAccount, String userPassword) {
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

//        String md5HexPassword = DigestUtils.md5Hex(SALT + userPassword);
        String md5HexPassword = userPassword;

        QueryWrapper<Evaluator> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", md5HexPassword);
        Evaluator evaluator = evaluatorMapper.selectOne(queryWrapper);

        if (evaluator == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码不正确");
        }
        EvaluatorVO evaluatorVO = new EvaluatorVO();
        BeanUtils.copyProperties(evaluator,evaluatorVO);
        return evaluatorVO;
    }
}
