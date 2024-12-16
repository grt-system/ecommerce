package com.imd.ecommerce.service;

import com.imd.ecommerce.model.Sell;
import com.imd.ecommerce.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellService {

    @Autowired
    private SellRepository sellRepository;

    public Sell createSell(){

    }
}
