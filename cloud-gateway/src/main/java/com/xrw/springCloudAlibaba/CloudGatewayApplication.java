package com.xrw.springCloudAlibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @program: LightFileManagement
 * @description: 启动类
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-03 17:55
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cloud-auth", r -> r.path("/auth/**")
                        .uri("lb://cloud-auth"))
                .route("cloud-mq", r -> r.path("/mq/**")
                        .uri("lb://cloud-mq"))
                .build();
    }
}
