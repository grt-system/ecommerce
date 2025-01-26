package com.imd.ecommerce.client;

import com.imd.ecommerce.dto.ProductDTO;
import com.imd.ecommerce.dto.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "store", url = "${store.url}")
public interface Store {

    @GetMapping("/product/{productId}")
    ProductDTO getProduct(@PathVariable("productId") long productId);

    @PostMapping("/sell")
    TransactionDTO createSell(@RequestParam("productId") long productId);
}
