package com.ahmet.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {

    @Value("${user-service.redis.host}") // yml'den çekiyoruz: ConfigServer/resources/config-repo/user-micro-service-application.yml
    private String redisHost;
    @Value("${user-service.redis.port}")
    private Integer redisPort;

    @Bean
    public LettuceConnectionFactory rediseBaglan() {
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(redisHost, redisPort)
        );
    }

}
