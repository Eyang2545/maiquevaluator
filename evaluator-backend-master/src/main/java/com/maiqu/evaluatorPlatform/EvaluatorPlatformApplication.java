package com.maiqu.evaluatorPlatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ht
 */
@SpringBootApplication
@MapperScan({"com.maiqu.evaluatorPlatform.mapper"})
public class EvaluatorPlatformApplication {

    public static void main(String[] args) {

        SpringApplication.run(EvaluatorPlatformApplication.class, args);
    }

}
