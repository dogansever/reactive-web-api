package com.sever.reactivewebapi.reactivewebapi.config;

import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
//@ComponentScan("com.sever")
//@EnableRedisRepositories(basePackages = "com.sever")
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, EmployeeEntity> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<String, EmployeeEntity>(factory, RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer(EmployeeEntity.class)));
    }
}
