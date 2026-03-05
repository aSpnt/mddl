package com.aspnt.mddl.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.aspnt.mddl.cache.EntityDefCacheResolver;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    public static final String SINGLETON_ENTITY_DEF_CACHE_NAME = "singletonEntityDefCache";
    public static final String SEARCH_ENTITY_COLLECTION_CACHE_NAME = "searchEntityCollectionCache";
    public static final String ENTITY_DEF_CACHE_RESOLVER = "entityDefCacheResolver";

    @Value("${app.cache.global-ttl}")
    private long globalTtl;

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(globalTtl, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

    @ConditionalOnProperty(name = "app.cache.enabled", havingValue = "true")
    @Bean(name = ENTITY_DEF_CACHE_RESOLVER)
    public EntityDefCacheResolver entityDefCacheResolver(CacheManager cacheManager) {
        return new EntityDefCacheResolver(cacheManager);
    }

    @ConditionalOnProperty(name = "app.cache.enabled", havingValue = "false")
    @Bean(name = ENTITY_DEF_CACHE_RESOLVER)
    public EntityDefCacheResolver noOpEntityDefCacheResolver() {
        return new EntityDefCacheResolver(new NoOpCacheManager());
    }
}
