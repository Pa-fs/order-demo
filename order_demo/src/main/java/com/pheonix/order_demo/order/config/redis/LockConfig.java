package com.pheonix.order_demo.order.config.redis;

import com.pheonix.order_demo.order.lock.LockKey;
import com.pheonix.order_demo.order.lock.LockKeySerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class LockConfig {

    @Bean
    public RedisConnectionFactory lockRedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
//        configuration.setDatabase(0);
//        configuration.setUsername("username");
//        configuration.setPassword("password");
        return new LettuceConnectionFactory(configuration);
    }

    @Bean(name = "lockRedisTemplate")
    public RedisTemplate<LockKey, Long> lockRedisTemplate() {
        RedisTemplate<LockKey, Long> lockRedisTemplate = new RedisTemplate<>();
        lockRedisTemplate.setConnectionFactory(lockRedisConnectionFactory());
        lockRedisTemplate.setKeySerializer(new LockKeySerializer());
        lockRedisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        //Jackson2JsonRedisSerializer
        return lockRedisTemplate;
    }
}
