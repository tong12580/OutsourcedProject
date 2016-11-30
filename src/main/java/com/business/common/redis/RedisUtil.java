package com.business.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuton on 2016/9/13.
 */
@Repository
public class RedisUtil<K,V> {

    @Resource
    private RedisTemplate redisTemplate;

    public void set(String key,Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key,Object value, Long timeOut) {
        redisTemplate.opsForValue().set(key,value,timeOut,TimeUnit.SECONDS);
    }

    public <V> V get(String key) {
        return (V) redisTemplate.opsForValue().get(key);
    }

    public void setMap(String key , Map<K,V> value) {
        redisTemplate.opsForHash().putAll(key,value);
    }

    public Set<V> getMapAllKey(String key){
        return redisTemplate.opsForHash().keys(key);
    }

    public List<V> getMapAllValue(String key){
        return redisTemplate.opsForHash().values(key);
    }

    public Object getMapValue(String key, Object MapKey) {
        return redisTemplate.opsForHash().get(key,MapKey);
    }

    public Map<K,V> getMap (String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(List<String> key) {
        redisTemplate.delete(key);
    }
}
