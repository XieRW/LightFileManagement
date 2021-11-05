package com.xrw.springCloudAlibaba.config.security;

import com.xrw.springCloudAlibaba.config.security.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证失败处理
 *
 * @author xearin
 * @date 2019/12/17 16:57
 */
@Component
@Slf4j
public class LoginAuthFailHandler extends AbstractResponseHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/json; charset=utf-8");
        String username = request.getParameter("username");
        log.info("登录失败：url={},method={},ip={},username={}", request.getRequestURI(),request.getMethod(),request.getRemoteAddr(),username);
        if(exception instanceof AuthenticationServiceException){
            String message = exception.getMessage();
            response.getWriter().write(Result.fail(10001,message).toString());
        }
    }

}
