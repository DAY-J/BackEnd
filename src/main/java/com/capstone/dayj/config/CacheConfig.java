package com.capstone.dayj.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("plans"); // value 설정, 여러 개 하고 싶으면 콤마로 구분해서 넣으면 됨.
        
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)  // 캐시 만료 시간 5분
                .maximumSize(1000));  // 최대 캐시 크기 1000개
        
        return cacheManager;
    }
}
