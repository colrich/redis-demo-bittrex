package io.pivotal.cctp.redisdemo;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(method.getName());
                if (objects != null && objects.length > 0) {
                    sb.append("--");
                    for (Object obj : objects) {
                        if (obj instanceof Map) sb.append(obj.hashCode()).append("-");
                        else if (obj instanceof String) sb.append(obj.toString()).append("-");
                    }
                }
                return sb.toString();
            }
        };
    }    
}