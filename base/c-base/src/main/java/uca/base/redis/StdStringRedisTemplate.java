package uca.base.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class StdStringRedisTemplate {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;

    public StdStringRedisTemplate(StringRedisTemplate stringRedisTemplate
                                  , RedissonClient redissonClient
    ) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redissonClient = redissonClient;
    }

    public String getWithLock(String key, int seconds, RedisCallback callback) {
        RLock lock = null;
        try {
            String result = stringRedisTemplate.opsForValue().get(key);
            if (null == result) {
                lock = this.redissonClient.getLock(key + "-lock");
                lock.lock(30, TimeUnit.SECONDS);
                result = stringRedisTemplate.opsForValue().get(key);
                if(null == result) {
                    result = callback.handle();
                    stringRedisTemplate.opsForValue().set(key, result, seconds, TimeUnit.SECONDS);
                }
            }
            return result;
        } finally {
            if(null != lock) {
                lock.unlock();
            }
        }
    }

    public interface RedisCallback {
        String handle();
    }


}
