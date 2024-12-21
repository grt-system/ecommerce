package com.imd.ecommerce.service;

import com.imd.ecommerce.dto.ProductDTO;
import com.imd.ecommerce.dto.TransactionDTO;
import com.imd.ecommerce.model.Sell;
import com.imd.ecommerce.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellService {

    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private FidelityService fidelityService;

    public TransactionDTO createSell(long productId, long userId, boolean ft){
          TransactionDTO transactionDTO = new TransactionDTO();
//        ProductDTO product = storeService.productDetails(productId);
//        double total = conversion(product.getValue());
//        transactionDTO.setId(storeService.processSell(productId));

        fidelityService.processBonus(userId, 30.10);

        return transactionDTO;
    }

    private double conversion(double value) {
        double rate = exchangeService.getRate();
        return value * rate;
    }
}
