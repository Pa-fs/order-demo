package com.pheonix.order_demo.order.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Slf4j
public class LockAdapter {

    private final RedisTemplate<LockKey, Long> lockRedisTemplate;
    private final ValueOperations<LockKey, Long> lockOperation;

    public LockAdapter(RedisTemplate<LockKey, Long> lockRedisTemplate) {
        this.lockRedisTemplate = lockRedisTemplate;
        this.lockOperation = lockRedisTemplate.opsForValue();
    }

    public Boolean holdLock(Long eventId, Long userId) {
        LockKey lockKey = LockKey.from(eventId);
        return lockOperation.setIfAbsent(lockKey, userId, Duration.ofSeconds(10));
    }

    public Long checkLock(Long eventId) {
        LockKey lockKey = LockKey.from(eventId);
        return lockOperation.get(lockKey);
    }

    public void clearLock(Long eventId) {
        lockRedisTemplate.delete(LockKey.from(eventId));
    }
}
