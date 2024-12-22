package com.imd.ecommerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "exchange", url = "${exchange.url}")
public interface Exchange {

    @GetMapping("/exchange")
    Double getExchange();
}
