package com.imd.ecommerce.client;

import com.imd.ecommerce.dto.ProductDTO;
import com.imd.ecommerce.dto.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "store", url = "${store.url}")
public interface Store {

    @GetMapping("/product")
    ProductDTO getProduct(@RequestParam("product") long productId);

    @PostMapping("/sell")
    TransactionDTO createSell(@RequestParam("id") long id);
}
