package com.xrw.springCloudAlibaba.exception;

/**
 * @author xearin
 */
public class ApiException extends RuntimeException {

    Integer errorCode;
    String msg;
    ApiError apiError;

    public ApiException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public ApiException(String msg) {
        super(msg);
        this.errorCode = 500;
        this.msg = msg;
    }

    public ApiException(ApiError apiError) {
        super(apiError.msg);
        this.errorCode = apiError.errorCode;
        this.msg = apiError.msg;
        this.apiError = apiError;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
