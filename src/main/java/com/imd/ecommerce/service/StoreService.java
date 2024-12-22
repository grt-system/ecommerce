package com.imd.ecommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imd.ecommerce.client.Store;
import com.imd.ecommerce.dto.ProductDTO;
import com.imd.ecommerce.dto.TransactionDTO;
import com.imd.ecommerce.exceptions.TransactionProcessingException;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class StoreService {

    @Autowired
    private Store storeClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Retry(name = "store", fallbackMethod = "productFallback")
    @Cacheable(value = "products", key = "#id")
    public ProductDTO productDetails(long id) {
        return storeClient.getProduct(id);
    }

    @Retry(name = "store", fallbackMethod = "transactionFallback")
    public TransactionDTO processSell(long id) {
//        try {
            return storeClient.createSell(id);
//        } catch (Exception e) {
//
//            throw new RuntimeException("Falha de omissão detected no Store.");
//        }
    }

    public ProductDTO productFallback(long id, Throwable throwable) {
        logger.error(" Usando fallback cache para o produto {}", id, throwable);

        String cacheKey = "products::" + id;
        String cachedProduct = redisTemplate.opsForValue().get(cacheKey);

        if (cachedProduct != null) {
            try {
                return objectMapper.readValue(cachedProduct, ProductDTO.class);
            } catch (Exception e) {
                logger.error("Erro ao desserializar produto do cache", e);
                throw new IllegalStateException("Erro ao desserializar produto do cache", e);
            }
        } else  {
            throw new IllegalStateException("Produto não encontrado no cache para ID: " + id);
        }
    }

    public TransactionDTO  transactionFallback(long id, Throwable throwable) {
        logger.error("Erro ao finalizar a transação para o ID {}.Detalhes:  ", id, throwable);

        throw new TransactionProcessingException("Não foi possível processar a venda com ID: " + id, throwable);
    }
}
