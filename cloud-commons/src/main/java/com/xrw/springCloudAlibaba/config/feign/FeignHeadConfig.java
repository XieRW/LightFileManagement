package com.xrw.springCloudAlibaba.config.feign;

import com.xrw.springCloudAlibaba.utils.login.LoginUserHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author ljy
 * @date 2021/6/13 16:07
 */
public class FeignHeadConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        try {
            String token = LoginUserHolder.getToken();

            String userStr = LoginUserHolder.getUserStr();

            requestTemplate.header("token", token);
            requestTemplate.header("user", userStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
