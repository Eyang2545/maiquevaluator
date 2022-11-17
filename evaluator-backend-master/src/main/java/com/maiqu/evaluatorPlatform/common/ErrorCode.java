package com.maiqu.evaluatorPlatform.common;

public enum ErrorCode {

    SUCCESS(0,"请求成功"),
    PARAMS_ERROR(40000,"请求参数错误"),
    PARAMS_NULL_ERROR(40001,"请求参数为空"),
    NO_AUTH(40100,"无权限"),
    FORBIDDEN(40300,"禁止操作"),
    NOT_LOGIN(40101,"不登陆"),
    SYSTEM_ERROR(50000,"系统内部异常")
    ;

    private final int code;
    /**
     * 状态码信息
     */
    private final String message;
    /**
     * 状态码描述，更多表示为什么
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
    ErrorCode(int code, String message) {
        this(code,message,"");
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
