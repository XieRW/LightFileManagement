package com.xrw.springCloudAlibaba;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: LightFileManagement
 * @description: 启动类
 * 开启springboot应用
 * 开启服务发现
 * 开启feign
 * 开启声明式缓存
 * 开启异步
 * 开启定时任务
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-03 18:12
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableAsync
@EnableScheduling
public class CloudAuthApplication {

}
