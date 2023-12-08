package com.example.pricecompareredis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties; // redis정보를 입력

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        //library 중에서 Lettuce 라이브러리를 사용할 거임
        //application.properties에 있는 내용을 통해 접근
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    @Primary // spring boot 에서 제공하는 RedisAutoConfiguration보다 이거를 우선적으로 쓰겠다.
    public RedisTemplate<String, Object> redisTemplate() { // 미리 세팅을 해놓는것
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // 직렬화
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}