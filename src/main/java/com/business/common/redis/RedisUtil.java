package com.business.common.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    public void setString(String key, String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public void setString(String key, String value, Long timeOut) {
        stringRedisTemplate.opsForValue().set(key,value,timeOut,TimeUnit.SECONDS);
    }

    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void set(String key,Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key,Object value, Long timeOut) {
        redisTemplate.opsForValue().set(key,value,timeOut,TimeUnit.SECONDS);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setMap(String key , Map<K,V> value) {
        redisTemplate.opsForHash().putAll(key,value);
    }

    public Set<K> getMapAllKey(String key){
        return redisTemplate.opsForHash().keys(key);
    }

    public List<K> getMapAllValue(String key){
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
        stringRedisTemplate.delete(key);
    }

    public void delete(List<String> key) {
        redisTemplate.delete(key);
        stringRedisTemplate.delete(key);
    }
}
