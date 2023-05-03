package com.google.couponfinder.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/5/3 17:21
 */

@Component
public class RedisUtils {
    private RedisTemplate redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void add(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }

    /**
     * 根据key，拿到val
     *
     * @param key key
     * @return val
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    public ZSetOperations getZSetOperations() {
        return this.redisTemplate.opsForZSet();
    }

}
