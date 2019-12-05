package com.business.common.redis;

import java.util.concurrent.TimeUnit;

/**
 * @author yutong
 * @version 1.0
 * 带锁的Redis
 * @since 2018/2/14 13:01
 */
public interface RedisLockUtil<K, V> {
    /**
     * @param key      {@link K}
     * @param value    {@link V}
     * @param timeOut  {@link Long}
     * @param timeUnit {@link TimeUnit}
     *                 原子更新数据 自定义时间单位
     * @see <a href="http://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     */
    Boolean setIfAbsent(K key, V value, Long timeOut, TimeUnit timeUnit);

    /**
     * @param key   {@link K}
     * @param value {@link V}
     *              原子更新数据 自定义时间单位
     * @see <a href="http://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     */
    Boolean setIfAbsent(K key, V value);

    /**
     * @param key     {@link K}
     * @param value   {@link V}
     * @param timeOut {@link Long}
     *                原子更新数据 自定义时间单位
     * @see <a href="http://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     */
    Boolean setIfAbsent(K key, V value, Long timeOut);
}
