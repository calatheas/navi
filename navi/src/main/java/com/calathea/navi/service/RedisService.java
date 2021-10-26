package com.calathea.navi.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisService {
    @Autowired
    private RedissonClient redissonClient;

    @Value("${spring.redis.key-prefix}")
    private String prefix;

    private final String delimiter = ":";

    private String getKey(String part) {
        return new StringBuilder()
                .append(prefix)
                .append(delimiter)
                .append(part)
                .toString();
    }

    public RLock getLock(String name) {
        return redissonClient.getLock(getKey(name));
    }
}
