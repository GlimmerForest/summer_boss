package com.william.boss.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableCaching
public class RedisConfig {

    @Resource
    private RedisConnectionFactory connectionFactory;

    /**
     * 修改序列号
     * 默认jdk自带序列化, 可视化很差
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new Jackson2JsonRedisSerializer<>(String.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(String.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setConnectionFactory(connectionFactory);

        return template;
    }

    /**
     * 默认是jdk序列化,调整为jackson序列化
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                .serializeKeysWith((RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer<>(String.class)).getKeySerializationPair()))
                .serializeValuesWith(RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer<>(String.class)).getValueSerializationPair());
    }

    /**
     * redis缓存管理
     */
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
    @Primary
    public CacheManager redisCacheManager() {

        return RedisCacheManager.builder().enableStatistics().cacheDefaults(redisCacheConfiguration())
                .cacheWriter(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory))
                .build();
    }
}