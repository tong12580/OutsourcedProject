package com.business.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yuton
 * @version 1.0
 * redis 公共方法
 * @since 2016/9/13 02:09
 */
public interface RedisUtil<K, V> {

    /**
     * @param key   {@link K}
     * @param value {@link V}
     *              更新数据 长时间有效
     */
    void set(K key, V value);

    /**
     * @param key     {@link K}
     * @param value   {@link V}
     * @param timeOut {@link TimeUnit TimeUnit.SECONDS}
     *                更新数据 默认时间秒
     */
    void set(K key, V value, Long timeOut);

    /**
     * @param key      {@link K}
     * @param value    {@link V}
     * @param timeOut  {@link Long}
     * @param timeUnit {@link TimeUnit}
     *                 更新数据 自定义时间单位
     */
    void set(K key, V value, Long timeOut, TimeUnit timeUnit);

    /**
     * @param key {@link K}
     * @return V
     * 查询数据
     */
    V get(K key);

    /**
     * @param key   {@link K}
     * @param value {@link V}
     *              插入Map对象数据
     */
    void setMap(K key, Map<K, V> value);

    /**
     * @param key {@link K}
     * @return Set<K>
     * 获取Map对象的所以键
     */
    Set<K> getMapAllKeys(K key);

    /**
     * @param key {@link K}
     * @return List<V>
     * 获取Map对象的所有值
     */
    List<V> getMapAllValues(K key);

    /**
     * @param key    {@link K}
     * @param MapKey {@link Map}
     * @return V
     * 获取Map键对应的值
     */
    V getMapValue(K key, K MapKey);

    /**
     * @param key {@link K}
     * @return Map
     * 获取整个Map对象
     */
    Map<K, V> getMap(K key);

    /**
     * @param key {@link K}
     *            删除数据
     */
    void delete(K key);

    /**
     * @param keys {@link List}
     *             批量删除数据
     */
    void delete(List<K> keys);

    /**
     * @return Boolean
     * Redis连接校验
     */
    Boolean ping();

    /**
     * @return Long
     * 数据量
     */
    Long dbSize();

    /**
     * @return Boolean
     * 清空配置对应数据库
     */
    Boolean flushDB();

    /**
     * @param k {@link K}
     * @return Long
     * 获取过期时间
     */
    Long expirationTime(K k);

    /**
     * 是否有key
     *
     * @param k K
     * @return boolean
     */
    Boolean hasKey(K k);

    /**
     * @param k K
     * @param v V
     *          redis 向队列中添加元素
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
