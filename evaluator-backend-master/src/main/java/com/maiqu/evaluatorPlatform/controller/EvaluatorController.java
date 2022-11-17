package com.maiqu.evaluatorPlatform.controller;

import com.maiqu.evaluatorPlatform.common.BaseResponse;
import com.maiqu.evaluatorPlatform.common.ErrorCode;
import com.maiqu.evaluatorPlatform.common.ResultUtils;
import com.maiqu.evaluatorPlatform.exception.BusinessException;
import com.maiqu.evaluatorPlatform.model.User;
import com.maiqu.evaluatorPlatform.model.entity.Evaluator;
import com.maiqu.evaluatorPlatform.model.request.EvaluatorLoginRequest;
import com.maiqu.evaluatorPlatform.model.request.UserLoginRequest;
import com.maiqu.evaluatorPlatform.model.request.UserRegisterRequest;
import com.maiqu.evaluatorPlatform.model.vo.EvaluatorVO;
import com.maiqu.evaluatorPlatform.service.EvaluatorService;
import com.maiqu.evaluatorPlatform.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author ht
 */
@Api(tags = "测评师模块")
@RestController
@RequestMapping("/api/evaluator")
public class EvaluatorController {

    @Resource
    private EvaluatorService evaluatorService;

    @PostMapping("/login")
    public BaseResponse<String> evaluatorLogin(@RequestBody EvaluatorLoginRequest evaluatorLoginRequest) {
        if(evaluatorLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"对象为空");
        }
        String userAccount = evaluatorLoginRequest.getUserAccount();
        String userPassword = evaluatorLoginRequest.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"参数为空");
        }
        String token = evaluatorService.doLoginByToken(userAccount,userPassword);
        return ResultUtils.success(token);
    }

    @GetMapping("/current")
    public BaseResponse<EvaluatorVO> getCurrent(HttpServletRequest request){
        if(request == null) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        EvaluatorVO evaluatorVO = evaluatorService.getLoginUserByToken(request);
        return ResultUtils.success(evaluatorVO);
    }




}
