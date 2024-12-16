package com.imd.ecommerce.controller;

import com.imd.ecommerce.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce")
public class EcommerceController {

    @Autowired
    private ExchangeService exchangeService;
}
