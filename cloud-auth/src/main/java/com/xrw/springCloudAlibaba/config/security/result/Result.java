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
    private int errorcode;

    /**
     * 返回的提示信息
     */
    private String msg;

    /**
     * 返回的数据对象
     */
    private Object data;


    private Result(int errorcode, Object data) {
        this.errorcode = errorcode;
        this.msg = ResultMsg.msg(errorcode);
        this.data = data;
    }

    private Result(int errorcode, String msg, Object data) {
        this.errorcode = errorcode;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data){
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result fail(int errorcode){
        return new Result(errorcode, null);
    }

    public static Result fail(int errorcode, String msg){
        return new Result(errorcode, msg, null);
    }


    public static Result fail(String msg){
        return new Result(ResultCode.FAIL, msg, null);
    }


    public String toJson(){
        return JsonUtil.toJson(this);
    }


    public int getCode() {
        return errorcode;
    }

    public void setCode(int errorcode) {
        this.errorcode = errorcode;
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
