package com.xrw.springCloudAlibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * @program: LightFileManagement
 * @description: 主启动类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-20 19:54
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class CloudFileApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.setProperty("nacos.standalone", "true");
        SpringApplication.run(CloudFileApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CloudFileApplication.class);
    }
}
