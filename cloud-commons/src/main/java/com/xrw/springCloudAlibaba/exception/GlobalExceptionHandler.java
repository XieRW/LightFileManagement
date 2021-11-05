package com.xrw.springCloudAlibaba.exception;

import com.xrw.springCloudAlibaba.vo.ResponseJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author xearin
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String SQL_ERROR = "22001";
    public static final String SQL_LONG_ERROR = "Data too long for column";
    public static final String HY = "HY000";
    public static final String INC = "Incorrect string value";

    public static  String getExceptionAllinformation(Exception ex){
        StringBuilder sOut = new StringBuilder();
        sOut.append(ex.getMessage());
        sOut.append("\r\n");
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut.append("\tat ");
            sOut.append(s);
            sOut.append("\r\n");
        }
        return sOut.toString();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseJSON handleApiException(ApiException e) {
        e.printStackTrace();
        return new ResponseJSON(e.errorCode, e.msg);
    }@ExceptionHandler(Exception.class)
    public ResponseJSON exception(ApiException e) {
        log.error(getExceptionAllinformation(e));
        return new ResponseJSON(ApiError.DATA_FETCH_ERROR);
    }

    /**
     * 捕获异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseJSON handleException(Exception e) {
        log.error(getExceptionAllinformation(e));
        if (e instanceof NumberFormatException) {
            return new ResponseJSON(ApiError.NUMBER_FORMAT_EXCEPTION);
        }
        if (e instanceof DuplicateKeyException) {
            return new ResponseJSON(ApiError.DATA_EXISTS);
        }
        if (e instanceof NullPointerException) {
            e.printStackTrace();
            return new ResponseJSON(ApiError.NULL_POINT_EXCEPTION);
        }
        if (e instanceof MethodArgumentTypeMismatchException) {
            e.printStackTrace();
            return new ResponseJSON(ApiError.NUMBER_FORMAT_EXCEPTION);
        }
        if (e instanceof DataAccessException) {
            e.printStackTrace();
            return new ResponseJSON(ApiError.NULL_POINT_EXCEPTION);
        }
        if (e instanceof IndexOutOfBoundsException) {
            e.printStackTrace();
            return new ResponseJSON(ApiError.NULL_POINT_EXCEPTION);
        }
        if (e instanceof MaxUploadSizeExceededException) {
            //上传的文件大小超过限制
            e.printStackTrace();
            return new ResponseJSON(-1, e.getMessage());
        }
        return new ResponseJSON(-1, e.getMessage());
    }

}
