package ru.softmachine.odyssey.backend.cms.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import ru.softmachine.odyssey.backend.cms.dto.EntityDefDto;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * Позволяет сгруппировать данные по Entity Def, что позволяет
 * чистить управлять кешем в дефинициях независимо
 */
@Slf4j
public class EntityDefCacheResolver implements CacheResolver {

    public static final String ENTITY_DEF_CACHE_PREFIX = "entityDefCache_";

    private final CacheManager cacheManager;

    public EntityDefCacheResolver(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        // Logic to determine cache name dynamically
        String entityDefCacheName = getEntityDefCode(context);

        Cache cache = cacheManager.getCache(entityDefCacheName);
        if (cache == null) {
            log.warn("Could not create cache for {}", entityDefCacheName);
            return Collections.emptyList();
        }
        return Collections.singletonList(cache);
    }

    private String getEntityDefCode(CacheOperationInvocationContext<?> context) {
        for (var i = 0; i <= context.getMethod().getParameters().length - 1; i++) {
            if (context.getMethod().getParameters()[i].getName().equals("entityDefId")
                    && context.getMethod().getParameters()[i].getType().equals(UUID.class)) {
                return ENTITY_DEF_CACHE_PREFIX + context.getArgs()[i].toString();
            }
            if (context.getMethod().getParameters()[i].getName().equals("entityDef")
                    && context.getMethod().getParameters()[i].getType().equals(EntityDef.class)
                    && ((EntityDef) context.getArgs()[i]).getId() != null) {
                return ENTITY_DEF_CACHE_PREFIX + ((EntityDef) context.getArgs()[i]).getId().toString();
            }
            if (context.getMethod().getParameters()[i].getName().equals("entityDef")
                    && context.getMethod().getParameters()[i].getType().equals(EntityDefDto.class)
                    && ((EntityDef) context.getArgs()[i]).getId() != null) {
                return ENTITY_DEF_CACHE_PREFIX + ((EntityDefDto) context.getArgs()[i]).getId().toString();
            }
        }
        throw new IllegalArgumentException("Cache name parameter not found in method arguments");
    }
}
