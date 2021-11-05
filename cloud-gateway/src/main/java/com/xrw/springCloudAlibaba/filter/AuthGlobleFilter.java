package com.xrw.springCloudAlibaba.filter;

import com.google.gson.JsonObject;
import com.xrw.springCloudAlibaba.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: LightFileManagement
 * @description: gateway过滤器
 * @author: 谢荣旺 1429382875@qq.com
 * @create: 2021-11-04 09:37
 **/
@Slf4j
@Component
public class AuthGlobleFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 路径匹配器
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 不做过滤处理的路径
     */
    private final static Set<String> IGNORE_URL = new HashSet<>(100);
    /**
     * 内部接口
     */
    private final static String INNER_URL = "/**/inner/**";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        //内部服务接口，不允许外部访问
        if (antPathMatcher.match(INNER_URL, path)) {
            ServerHttpResponse response = exchange.getResponse();
            return out(response);
        }

        Mono<Void> ignoreUrlFilterVoidMono = ignoreUrlFilter(exchange, chain);
        return ignoreUrlFilterVoidMono == null ? serviceFilter(exchange, chain) : ignoreUrlFilterVoidMono;
    }

    private Mono<Void> ignoreUrlFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        for (String s : IGNORE_URL) {
            if (antPathMatcher.match(s, path)) {
                request = exchange.getRequest().mutate().header("isPublic", "1").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }

        return null;
    }

    private Mono<Void> serviceFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        //对于actuator端点添加响应头，否则admin识别application/json的数据会把admin client当初springBoot 1.X版本，导致版本不匹配
        if (antPathMatcher.match("/**/actuator/**",path)){
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", "application/vnd.spring-boot.actuator.v2+json;charset=UTF-8");
        }

        String token = exchange.getRequest().getHeaders().getFirst("token");
        String key = StringUtils.isEmpty(token) ? null : redisUtils.getKey("TOKEN:" + token + ":*");
        if (key == null) {
            ServerHttpResponse response = exchange.getResponse();
            return nullTokenOut(response, request);
        }
        log.info("request.getPath()>>>>{}", request.getPath());
        log.info("key:{}", key);
        String redisKey;

        String type = token.split("_")[0];
        switch (type) {
            case "PC":
            case "UDS":
            case "APP":
                int userId = Integer.parseInt(key.split(":")[2]);
                redisKey = type + "_user_" + userId;
                break;

            case "CONTACTORAPP":
                redisKey = "CONTACTORAPP_user_" + key.split(":")[2].replace("wechat", "");
                break;
            case "CONTACTORWX":
                redisKey = "CONTACTORWX_user_" + key.split(":")[2].replace("wechat", "");
                break;
            case "CONTACTORH5":
                redisKey = "CONTACTORH5_user_" + key.split(":")[2].replace("wechat", "");
                break;
            default:
                //匿名用户
                redisKey = "TOKEN:" + token + ":Anonymous";
        }


        //获取用户信息
        String userStr = redisUtils.get(redisKey);
        if (userStr == null) {
            ServerHttpResponse response = exchange.getResponse();
            return nullTokenOut(response, request);
        }
        log.info("用户信息userStr>>>>>>{}", userStr);
        request = exchange.getRequest().mutate().header("user", new String(userStr.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1))
                .header("isPublic", "0").build();

        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    private Mono<Void> nullTokenOut(ServerHttpResponse response, ServerHttpRequest request) {
        URI uri = request.getURI();

        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("errorcode", 401);
        message.addProperty("data", "未登录,禁止访问");
        message.addProperty("msg", "未登录,禁止访问");
        message.addProperty("uri", uri.toString());
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    private Mono<Void> out(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 28004);
        message.addProperty("data", "鉴权失败");
        message.addProperty("msg", "鉴权失败");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }

    static {
        IGNORE_URL.add("/auth/test/**");
        IGNORE_URL.add("/auth/permit/**");
        IGNORE_URL.add("/auth/captcha.jpg/**");
    }
}
