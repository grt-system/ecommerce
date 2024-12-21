package com.imd.ecommerce.service;

import com.imd.ecommerce.client.Exchange;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    @Autowired
    private Exchange exchangeClient;

    @Autowired
    private CacheManager cacheManager;

    //@CircuitBreaker(name = "exchange", fallbackMethod = "useLastExchangeRate")
    @Cacheable(value = "rate", key = "'exchange'")
    @Retry(name = "exchange", fallbackMethod = "cacheFallback")
    public double getRate() {
        double rate = exchangeClient.getExchange();
        return rate;
    }

    @CachePut(value = "rate", key = "'exchange'")
    public double saveCache(double rate) {
        return rate;
    }

    public Double cacheFallback(Throwable throwable) {
        Cache cache = cacheManager.getCache("rate");
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get("exchange");
            if (valueWrapper != null) {
                return (Double) valueWrapper.get();
            }
        }
        return null;
    }
}
