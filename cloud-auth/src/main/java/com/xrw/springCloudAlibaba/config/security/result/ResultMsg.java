package com.xrw.springCloudAlibaba.config.security.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单code msg映射
 * 国际化（如果需要）由前端完成，后端提供返回码以及描述即可
 *
 * @author leon
 * @Date 2019-11-15 10:00
 **/
public class ResultMsg {

    private static final Map<Integer, String> map = new HashMap<>();

    // TODO 更新描述

    static {
        map.put(ResultCode.SUCCESS, "成功");
        map.put(ResultCode.FAIL, "服务器异常");

        // 40XX
        map.put(ResultCode.UNAUTHORIZED, "未登陆");
        map.put(ResultCode.LOGIN_FAIL, "登陆失败");
        map.put(ResultCode.REGISTER_FAIL, "注册失败");
        map.put(ResultCode.INVALID_TOKEN, "TOKEN已过期或无效");
        map.put(ResultCode.INVALID_WX_PARAM, "无效的微信认证授权参数");
        // 5XXX
        map.put(ResultCode.DUTY_UPLOAD_INVALID_FILE,"请上传正确的文件格式！");
        map.put(ResultCode.DUTY_UPLOAD_FILE_NULL,"文件为空！");
    }

    public static String msg(int code) {
        return map.get(code);
    }

}
