package com.xrw.springCloudAlibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @program: LightFileManagement
 * @description: 主启动类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-02-07 18:51
 **/
@SpringBootApplication
public class CloudMqApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(CloudMqApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(CloudMqApplication.class);
    }
}
