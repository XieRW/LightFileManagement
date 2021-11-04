package com.xrw.springCloudAlibaba.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * @author xearin
 * @date 2021-05-05
 * 跨域请求头重复处理过滤器 避免出现 Multiple CORS header
 * 该问题 由NettyRoutingFilter这个过滤器，指定自定过滤器位于NettyWriteResponseFilter之后，去掉重复响应头
 */

@Component
public class CorsResponseHeaderFilter implements GlobalFilter, Ordered {

    @Override
    @SuppressWarnings("serial")
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.defer(() -> {
            exchange.getResponse().getHeaders().entrySet().stream()
                    .filter(kv -> (kv.getValue() != null && kv.getValue().size() > 1))
                    .filter(kv -> (kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
                            || kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS)))
                    .forEach(kv -> kv.setValue(new ArrayList<String>() {{
                        add(kv.getValue().get(0));
                    }}));
            return chain.filter(exchange);
        }));
    }

    @Override
    public int getOrder() {
        // 指定位于 NettyWriteResponseFilter 处理完响应体后移除重复 CORS 响应头
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }
}
