package com.sever.reactivewebapi.reactivewebapi.config;

import com.sever.reactivewebapi.reactivewebapi.dao.entity.EmployeeEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, EmployeeEntity> reactiveJsonPostRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, EmployeeEntity> serializationContext = RedisSerializationContext
                .<String, EmployeeEntity>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(EmployeeEntity.class))
                .build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
}
