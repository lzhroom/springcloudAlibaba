package com.qf.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {

    public AgeRoutePredicateFactory() {
        super(Config.class);
    }

    //读取配置文件中的值 将其赋予给配置类里面的属性上
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("minAge","maxAge");
    }

    //定义具体的断言业务逻辑
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                String str = serverWebExchange.getRequest().getQueryParams().getFirst("age");
                if(StringUtils.isNotEmpty(str)){
                    int age = Integer.parseInt(str);
                    if(age < config.getMaxAge() && age > config.getMinAge()){
                        return true;
                    }else{
                        return false;
                    }
                }
                return false;
            }
        };
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config{
        private int minAge;
        private int maxAge;
    }
}
