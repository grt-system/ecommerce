package com.imd.ecommerce.client;

import com.imd.ecommerce.dto.ExchangeDTO;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "exchange", url = "${exchange.url}")
public interface Exchange {

    @GetMapping("/")
    ExchangeDTO getExchange() throws FeignException;
}
