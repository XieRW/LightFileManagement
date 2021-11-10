package com.xrw.springCloudAlibaba.config.security;

import com.alibaba.fastjson.JSONObject;
import com.xrw.springCloudAlibaba.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 登出操作 删除token
 * @version desc
 * @QUTHOR hdq
 * @date 2021/6/16
 */
@Component
public class LoginOutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取token
        String token = request.getHeader("token");
        String key = redisUtils.getKey("TOKEN:" + token + ":*");
        if(key == null){
            responseError(response);
            return;
        }
        if (redisUtils.delete(key)){
            response(response);
        }else {
            responseError(response);
        }
    }


    public void  response(HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorcode",0);
        jsonObject.put("msg","success");
        String responseStr = jsonObject.toJSONString();
        PrintWriter out = response.getWriter();
        out.write(new String(responseStr.getBytes(), StandardCharsets.UTF_8));
        out.flush();
    }

    public void  responseError(HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorcode",500);
        //todo 解决中文乱码问题
        jsonObject.put("msg","token error");
        String responseStr = jsonObject.toJSONString();
        PrintWriter out = response.getWriter();
        out.write(new String(responseStr.getBytes(), StandardCharsets.UTF_8));
        out.flush();
    }
}
