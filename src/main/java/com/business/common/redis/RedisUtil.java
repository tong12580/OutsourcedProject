package com.business.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuton on 2016/9/13.
 */
public interface RedisUtil<K,V> {

    /**
     * @description 更新数据 长时间有效
     * @param key
     * @param value
     */
    void set(K key, V value);

    /**
     * @description 更新数据 默认时间秒
     * @param key
     * @param value
     * @param timeOut {@link TimeUnit TimeUnit.SECONDS}
     */
    void set(K key, V value, Long timeOut);

    /**
     * @description 更新数据 自定义时间单位
     * @param key
     * @param value
     * @param timeOut
     * @param timeUnit
     */
    void set(K key, V value, Long timeOut, TimeUnit timeUnit);

    /**
     * @description 查询数据
     * @param key
     * @return
     */
    V get(K key);

    /**
     * @description 插入Map对象数据
     * @param key
     * @param value
     */
    void setMap(K key, Map<K, V> value);

    /**
     * @description 获取Map对象的所以键
     * @param key
     * @return
     */
    Set<K> getMapAllKeys(K key);

    /**
     * @description 获取Map对象的所有值
     * @param key
     * @return
     */
    List<V> getMapAllValues(K key);

    /**
     * @description 获取Map键对应的值
     * @param key
     * @param MapKey
     * @return
     */
    V getMapValue(K key, K MapKey);

    /**
     * @description 获取整个Map对象
     * @param key
     * @return
     */
    Map<K, V> getMap(K key);

    /**
     * @description 删除数据
     * @param key
     */
    void delete(K key);

    /**
     * @description 批量删除数据
     * @param keys
     */
    void delete(List<K> keys);

    /**
     * @description Redis连接校验
     * @return
     */
    Boolean ping();

    /**
     * @description 数据量
     * @return
     */
    Long dbSize();

    /**
     * @description 清空配置对应数据库
     * @return
     */
    Boolean flushDB();
}
