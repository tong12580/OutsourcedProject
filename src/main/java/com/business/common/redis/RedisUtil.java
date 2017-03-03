package com.business.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yuton
 * @version 1.0
 * @description redis 公共方法
 * @since 2016/9/13 02:09
 */
public interface RedisUtil<K,V> {

    /**
     * @description 更新数据 长时间有效
     * @param key {@link K}
     * @param value {@link V}
     */
    void set(K key, V value);

    /**
     * @description 更新数据 默认时间秒
     * @param key {@link K}
     * @param value {@link V}
     * @param timeOut {@link TimeUnit TimeUnit.SECONDS}
     */
    void set(K key, V value, Long timeOut);

    /**
     * @description 更新数据 自定义时间单位
     * @param key {@link K}
     * @param value {@link V}
     * @param timeOut {@link Long}
     * @param timeUnit  {@link TimeUnit}
     */
    void set(K key, V value, Long timeOut, TimeUnit timeUnit);

    /**
     * @description 查询数据
     * @param key {@link K}
     * @return V
     */
    V get(K key);

    /**
     * @description 插入Map对象数据
     * @param key {@link K}
     * @param value {@link V}
     */
    void setMap(K key, Map<K, V> value);

    /**
     * @description 获取Map对象的所以键
     * @param key {@link K}
     * @return Set<K>
     */
    Set<K> getMapAllKeys(K key);

    /**
     * @description 获取Map对象的所有值
     * @param key {@link K}
     * @return List<V>
     */
    List<V> getMapAllValues(K key);

    /**
     * @description 获取Map键对应的值
     * @param key {@link K}
     * @param MapKey {@link Map}
     * @return V
     */
    V getMapValue(K key, K MapKey);

    /**
     * @description 获取整个Map对象
     * @param key {@link K}
     * @return Map
     */
    Map<K, V> getMap(K key);

    /**
     * @description 删除数据
     * @param key {@link K}
     */
    void delete(K key);

    /**
     * @description 批量删除数据
     * @param keys {@link List}
     */
    void delete(List<K> keys);

    /**
     * @description Redis连接校验
     * @return Boolean
     */
    Boolean ping();

    /**
     * @description 数据量
     * @return Long
     */
    Long dbSize();

    /**
     * @description 清空配置对应数据库
     * @return Boolean
     */
    Boolean flushDB();

    /**
     * @description 获取过期时间
     * @param k {@link K}
     * @return Long
     */
    Long expirationTime(K k);
}
