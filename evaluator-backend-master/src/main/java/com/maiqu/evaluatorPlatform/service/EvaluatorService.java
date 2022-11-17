package com.maiqu.evaluatorPlatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maiqu.evaluatorPlatform.model.User;
import com.maiqu.evaluatorPlatform.model.entity.Evaluator;
import com.maiqu.evaluatorPlatform.model.vo.EvaluatorVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ht
 */
public interface EvaluatorService extends IService<Evaluator> {


    String doLoginByToken(String userAccount, String userPassword);

    EvaluatorVO getLoginUserByToken(HttpServletRequest request);
}
