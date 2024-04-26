package com.qf.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    public LogGatewayFilterFactory() {
        super(Config.class);
    }

    //读取配置文件里面的值 并将其赋予给配置类里面的属性
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("debugg","info");
    }

    //定义具体的过滤业务逻辑
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if(config.getDebugg().equals("debugg")){
                    System.out.println("开启了debugg的日志级别");
                }
                if(config.getInfo().equals("info")){
                    System.out.println("开启了info的日志级别");
                }
                return chain.filter(exchange);
            }
        };
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config{
        private String debugg;
        private String info;
    }
}
