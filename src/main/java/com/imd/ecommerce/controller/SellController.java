package com.imd.ecommerce.controller;

import com.imd.ecommerce.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sell")
public class SellController {

    @Autowired
    private SellService sellService;

    @PostMapping("/")
    public ResponseEntity<?> createSell(@RequestParam String product, @RequestParam String user, @RequestParam boolean ft){
        //enviar request para o store
        //pegar valor do produto
        //
        return  ResponseEntity.ok().body("ok");
    }
}
