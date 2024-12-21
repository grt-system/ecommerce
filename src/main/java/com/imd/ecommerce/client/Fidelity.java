package com.imd.ecommerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fidelity", url = "${fidelity.url}")
public interface Fidelity {

    @PostMapping("/bonus")
    ResponseEntity<?> sendBonus(@RequestParam("user") long user , @RequestParam("product") double bonus);
}
