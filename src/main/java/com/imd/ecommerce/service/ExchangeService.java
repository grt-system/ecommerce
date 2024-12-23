package com.imd.ecommerce.service;

import com.imd.ecommerce.client.Exchange;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    @Autowired
    private Exchange exchangeClient;

    @Autowired
    private CacheManager cacheManager;

    @Autowired private RedisTemplate<String, String> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    @Cacheable(value = "rate", key = "'exchange'")
    @Retry(name = "exchange", fallbackMethod = "cacheFallback")
    public double getRate() {
        return exchangeClient.getExchange();
    }

    @CachePut(value = "rate", key = "'exchange'")
    public double saveCache(double rate) {
        return rate;
    }

    public double cacheFallback(Throwable throwable) {
        try {
            String cache = redisTemplate.opsForValue().get("rate::exchange");
            if (cache != null) {
                return Double.parseDouble(cache);
            } else {
                throw new IllegalStateException("No value found in cache for 'rate::exchange'");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error accessing cache: " + e.getMessage(), e);
        }
    }
}