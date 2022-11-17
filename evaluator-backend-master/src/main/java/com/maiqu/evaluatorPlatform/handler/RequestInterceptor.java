package com.maiqu.evaluatorPlatform.handler;

import com.maiqu.evaluatorPlatform.common.ErrorCode;
import com.maiqu.evaluatorPlatform.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RequestInterceptor implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if(request.getMethod().equals("OPTIONS")){
                    response.setStatus(HttpServletResponse.SC_OK);
                    return true;
                }
                String token = request.getHeader("token");

                if(token != null && redisTemplate.opsForValue().get(token) != null){
                    //每次认证就重置30min
                    redisTemplate.expire(token,30, TimeUnit.MINUTES);
                    log.info("token:通过拦截器了");
                    return true;
                }
                //401表示未登录
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                log.error("token:没有通过拦截器");
                throw new BusinessException(ErrorCode.NOT_LOGIN);
            }
        }).excludePathPatterns("/**");
        //"/user/loginBySession","/user/loginByToken","/user/register","/**/doc.*",
//                        "/**/swagger-ui.*",
//                        "/**/swagger-resources",
//                        "/**/webjars/**",
//                        "/**/v2/api-docs/**",
//                        "/v3/api-docs",
//                        "/**/favicon.*"
    }

}
