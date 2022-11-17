package com.maiqu.evaluatorPlatform.common;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0,data,true);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode,String message,String description) {
        return new BaseResponse<>(errorCode,message,description);
    }

    public static <T> BaseResponse<T> error(int code,String message,String description) {
        return new BaseResponse<>(code,message,description);
    }


}
