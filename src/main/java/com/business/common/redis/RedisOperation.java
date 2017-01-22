package com.business.common.redis;

import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yuton
 * @version 1.0
 * @description
 * @since 2017/1/5 15:51
 */
@Repository
public class RedisOperation<K, V> implements RedisUtil<K, V> {
    @Resource
    private RedisTemplate<K, V> redisTemplate;

    @Override
    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(K key, V value, Long timeOut) {
        this.set(key, value, timeOut, TimeUnit.SECONDS);
    }

    @Override
    public void set(K key, V value, Long timeOut, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeOut, timeUnit);
    }

    @Override
    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setMap(K key, Map<K, V> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<K> getMapAllKeys(K key) {
        return (Set<K>) redisTemplate.opsForHash().keys(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<V> getMapAllValues(K key) {
        return (List<V>) redisTemplate.opsForHash().values(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V getMapValue(K key, K MapKey) {
        return (V) redisTemplate.opsForHash().get(key, MapKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<K, V> getMap(K key) {
        return (Map<K, V>) redisTemplate.opsForHash().entries(key);
    }

    @Override
    public void delete(K key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delete(List<K> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public Boolean ping() {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> redisConnection.ping().equals("PONG"));
    }

    @Override
    public Long dbSize() {
        return redisTemplate.execute(RedisServerCommands::dbSize);
    }

    @Override
    public Boolean flushDB() {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            redisConnection.flushDb();
            return redisConnection.dbSize() == 0;
        });
    }
}
