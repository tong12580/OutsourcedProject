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
public interface RedisUtil<K, V> {

    /**
     * @param key   {@link K}
     * @param value {@link V}
     * @description 更新数据 长时间有效
     */
    void set(K key, V value);

    /**
     * @param key     {@link K}
     * @param value   {@link V}
     * @param timeOut {@link TimeUnit TimeUnit.SECONDS}
     * @description 更新数据 默认时间秒
     */
    void set(K key, V value, Long timeOut);

    /**
     * @param key      {@link K}
     * @param value    {@link V}
     * @param timeOut  {@link Long}
     * @param timeUnit {@link TimeUnit}
     * @description 更新数据 自定义时间单位
     */
    void set(K key, V value, Long timeOut, TimeUnit timeUnit);

    /**
     * @param key {@link K}
     * @return V
     * @description 查询数据
     */
    V get(K key);

    /**
     * @param key   {@link K}
     * @param value {@link V}
     * @description 插入Map对象数据
     */
    void setMap(K key, Map<K, V> value);

    /**
     * @param key {@link K}
     * @return Set<K>
     * @description 获取Map对象的所以键
     */
    Set<K> getMapAllKeys(K key);

    /**
     * @param key {@link K}
     * @return List<V>
     * @description 获取Map对象的所有值
     */
    List<V> getMapAllValues(K key);

    /**
     * @param key    {@link K}
     * @param MapKey {@link Map}
     * @return V
     * @description 获取Map键对应的值
     */
    V getMapValue(K key, K MapKey);

    /**
     * @param key {@link K}
     * @return Map
     * @description 获取整个Map对象
     */
    Map<K, V> getMap(K key);

    /**
     * @param key {@link K}
     * @description 删除数据
     */
    void delete(K key);

    /**
     * @param keys {@link List}
     * @description 批量删除数据
     */
    void delete(List<K> keys);

    /**
     * @return Boolean
     * @description Redis连接校验
     */
    Boolean ping();

    /**
     * @return Long
     * @description 数据量
     */
    Long dbSize();

    /**
     * @return Boolean
     * @description 清空配置对应数据库
     */
    Boolean flushDB();

    /**
     * @param k {@link K}
     * @return Long
     * @description 获取过期时间
     */
    Long expirationTime(K k);

    /**
     * 是否有key
     * @param k K
     * @return boolean
     */
    boolean hasKey(K k);

    /**
     * @param k K
     * @param v V
     * @description redis 向队列中添加元素
     */
    void pushList(K k, V v);

    /**
     * @param k K
     * @return V
     * @description 移除并获取最后一个 (出队)
     */
    V popRightList(K k);

    /**
     * @param k K
     * @return V
     * @description 移除并获取第一个元素 (出栈)
     */
    V popLeftList(K k);
}
