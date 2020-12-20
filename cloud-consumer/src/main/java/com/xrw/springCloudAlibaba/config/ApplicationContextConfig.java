package com.xrw.springCloudAlibaba.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: LightFileManagement
 * @description: rest接口调用模板类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-21 00:12
 **/
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
