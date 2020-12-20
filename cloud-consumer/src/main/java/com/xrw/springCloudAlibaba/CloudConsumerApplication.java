package com.xrw.springCloudAlibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: LightFileManagement
 * @description: 主启动类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-21 00:03
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CloudConsumerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CloudConsumerApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(CloudConsumerApplication.class);
    }
}
