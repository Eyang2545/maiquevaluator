package com.maiqu.evaluatorPlatform.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    @JsonIgnore
    private final long serialVersionUID=-2082203801443301445L;

    @JsonProperty("isSuccess")
    boolean success = false;
    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse() {

    }

    public BaseResponse(int code,T data,String message,boolean success,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success= success;
        this.description = description;
    }

    public BaseResponse(int code, T data,boolean success) {
        this(code,data,"",success,"");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(),null,errorCode.getMessage(),false,errorCode.getDescription());
    }

    public BaseResponse(ErrorCode errorCode,String message,String description) {
        this(errorCode.getCode(),null,message,false,description);
    }

    public BaseResponse(int code,String message,String description) {
        this(code,null,message,false,description);
    }



}
