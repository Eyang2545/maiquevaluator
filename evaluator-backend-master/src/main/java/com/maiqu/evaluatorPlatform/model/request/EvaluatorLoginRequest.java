package com.maiqu.evaluatorPlatform.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ht
 */
@Data
public class EvaluatorLoginRequest implements Serializable {
    private String userAccount;

    private String userPassword;
}
