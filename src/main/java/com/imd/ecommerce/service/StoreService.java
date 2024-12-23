package com.imd.ecommerce.service;

import com.imd.ecommerce.client.Store;
import com.imd.ecommerce.dto.ProductDTO;
import com.imd.ecommerce.dto.TransactionDTO;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class StoreService {

    @Autowired
    private Store store;

    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Retry(name = "store", fallbackMethod = "productFallback")
    public ProductDTO productDetails(long id) {
        return store.getProduct(id);
    }

    @Retry(name = "store", fallbackMethod = "transactionFallback")
    public long processSell(long id) {
        return store.createSell(id);
    }

    public ProductDTO productFallback(Throwable throwable) {
        ProductDTO productDTO = new ProductDTO("teste", 30.00);
        //TO-DO
        return productDTO;
    }

    public void transactionFallback(Throwable throwable) {
        //TO-DO
    }
}
