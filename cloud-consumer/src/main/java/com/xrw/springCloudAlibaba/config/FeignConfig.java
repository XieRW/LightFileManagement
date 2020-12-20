package com.xrw.springCloudAlibaba.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: LightFileManagement
 * @description: openFeign日志监控
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2020-12-21 02:10
 **/
@Configuration
public class FeignConfig {

    /**
     * @Description: 配置日志级别
     * NONE：默认的，不显示任何日志
     * BASIC：仅记录请求方法、URL、响应状态码及执行时间
     * HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息
     * FULL：除了HEAdERS中定义的信息之外，还有请求和响应的正文及元数据
     * @param :
     * @return: feign.Logger.Level
     * @Author: 谢荣旺
     * @Date: 2020/12/21
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
