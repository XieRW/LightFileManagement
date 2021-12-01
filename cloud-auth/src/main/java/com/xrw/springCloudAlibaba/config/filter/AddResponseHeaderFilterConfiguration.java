package com.xrw.springCloudAlibaba.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: LightFileManagement
 * @description:
 * @author: xearin 1429382875@qq.com
 * @create: 2021-12-01 12:00
 **/
@Configuration
public class AddResponseHeaderFilterConfiguration {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AddResponseHeaderFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(-2147483647);
        return registration;
    }
}
