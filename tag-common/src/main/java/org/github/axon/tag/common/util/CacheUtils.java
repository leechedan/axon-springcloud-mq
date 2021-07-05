package org.github.axon.tag.common.util;

import org.github.axon.tag.common.exception.BusinessException;
import org.axonframework.eventsourcing.AggregateCacheEntry;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.impl.config.persistence.DefaultPersistenceConfiguration;
import org.ehcache.jsr107.Eh107Configuration;
import org.ehcache.jsr107.EhcacheCachingProvider;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 *
 * @author lee
 * @create 2018-01-26 下午5:20
 **/
public enum CacheUtils {
    ;
    /**
     * cache的存储目录.
     * 若使用docker, 考虑是否需要做挂载配置
     */
    private static final String CACHE_STORAGE_PATH = "/tmp/cache";

    /**
     * 缓存可用的堆内存size, 以 对象 为单位
     * 谨慎使用，影响gc效率
     */
    private static final long CACHE_HEAD_SIZE = 8L;

    /**
     * 缓存可用的堆外内存size, 以 M 为单位
     */
    private static final long CACHE_OFF_HEAD_SIZE = 2L;

    /**
     * 缓存可用的磁盘size, 以 M 为单位
     */
    private static final long CACHE_DISK_SIZE = 16L;

    /**
     * 缓存过期时间, 以 秒 为单位
     */
    private static final long CACHE_EXPIRATION_SECONDS = 10;

    /**
     * 缓存Manager
     */
    private static CacheManager cacheManager;

    static {
        // JCache规范, 配置具体缓存实现ehcache
        CachingProvider cachingProvider = Caching.getCachingProvider();
        EhcacheCachingProvider ehcacheProvider = (EhcacheCachingProvider) cachingProvider;
        // 配置缓存文件目录
        String str = UUID.randomUUID().toString();
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration(ehcacheProvider.getDefaultClassLoader(), new DefaultPersistenceConfiguration(new File(CACHE_STORAGE_PATH, str)));
        // JCache缓存管理器
        cacheManager = ehcacheProvider.getCacheManager(ehcacheProvider.getDefaultURI(), defaultConfiguration);
        if (cacheManager == null) {
            throw new BusinessException("CacheUtils创建cacheManager异常");
        }
    }


    /**
     * 创建JCache规范的cache实例, 基于ehcache
     *
     * @param cacheName cache名
     * @return cache实例
     */
    public static Cache<String, AggregateCacheEntry> createCacheInstance(String cacheName) {

        // 用ehcache的配置,实现3级缓存细粒度配置
        CacheConfiguration<String, AggregateCacheEntry> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                // key & value 类型配置
                String.class, AggregateCacheEntry.class,
                // 3级缓存配置
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(CACHE_HEAD_SIZE, EntryUnit.ENTRIES)
                        .offheap(CACHE_OFF_HEAD_SIZE, MemoryUnit.MB)
                        .disk(CACHE_DISK_SIZE, MemoryUnit.MB))
                // 超时配置
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(CACHE_EXPIRATION_SECONDS, TimeUnit.SECONDS))).build();
        // JCache 缓存对象
        return cacheManager.createCache(cacheName,
                Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration));
    }


    /**
     * 创建JCache规范的cache实例, 基于ehcache
     *
     * @param cacheName
     * @param headSize
     * @param offHeadSize
     * @param diskSize
     * @param expirationTime
     * @return
     */
    public static Cache<String, AggregateCacheEntry> createCacheInstance(String cacheName, long headSize, long offHeadSize, long diskSize, long expirationTime) {

        // 用ehcache的配置,实现3级缓存细粒度配置
        CacheConfiguration<String, AggregateCacheEntry> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                // key & value 类型配置
                String.class, AggregateCacheEntry.class,
                // 3级缓存配置
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(headSize, EntryUnit.ENTRIES)
                        .offheap(offHeadSize, MemoryUnit.MB)
                        .disk(diskSize, MemoryUnit.MB))
                // 超时配置
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(expirationTime, TimeUnit.SECONDS))).build();
        // JCache 缓存对象
        return cacheManager.createCache(cacheName,
                Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration));
    }
}
