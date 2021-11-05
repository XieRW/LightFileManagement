package com.xrw.springCloudAlibaba.config.security.result;


import com.alibaba.fastjson.JSONObject;
import com.xrw.springCloudAlibaba.utils.JsonUtil;

/**
 * @author xearin
 * @Date 2019-11-15 10:00
 **/
public class Result {

    /**
     * 自定义状态吗
     */
    private int code;

    /**
     * 返回的提示信息
     */
    private String msg;

    /**
     * 返回的数据对象
     */
    private Object data;


    private Result(int code, Object data) {
        this.code = code;
        this.msg = ResultMsg.msg(code);
        this.data = data;
    }

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data){
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result fail(int code){
        return new Result(code, null);
    }

    public static Result fail(int code, String msg){
        return new Result(code, msg, null);
    }


    public static Result fail(String msg){
        return new Result(ResultCode.FAIL, msg, null);
    }


    public String toJson(){
        return JsonUtil.toJson(this);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
