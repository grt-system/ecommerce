package com.imd.ecommerce.controller;

import com.imd.ecommerce.dto.TransactionDTO;
import com.imd.ecommerce.service.ExchangeService;
import com.imd.ecommerce.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce")
public class EcommerceController {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private SellService sellService;

    @PostMapping("/buy")
    public ResponseEntity<?> createSell(@RequestParam long product, @RequestParam long user, @RequestParam boolean ft){
       try {
           TransactionDTO transactionDTO = sellService.createSell(product, user, ft);
           return ResponseEntity.ok().body("ok");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Create sell process failed: " + e.getMessage());
       }
    }
}
