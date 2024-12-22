package com.imd.ecommerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imd.ecommerce.client.Exchange;
import com.imd.ecommerce.client.Fidelity;
import com.imd.ecommerce.client.Store;
import com.imd.ecommerce.dto.ExchangeDTO;
import com.imd.ecommerce.dto.ProductDTO;
import com.imd.ecommerce.dto.TransactionDTO;
import com.imd.ecommerce.model.Sell;
import com.imd.ecommerce.repository.SellRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellService {

    @Autowired
    private Store storeClient;

    @Autowired
    private Fidelity fidelityClient;

    @Autowired
    private Exchange exchangeClient;

    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private FidelityService fidelityService;

    private static final Logger logger = LoggerFactory.getLogger(SellService.class);

    public TransactionDTO createSell(long productId, long userId, boolean ft) throws JsonProcessingException {
        if(ft) {
            ProductDTO product = storeService.productDetails(productId); // Detalhes do produto
            double total = conversion(product.getValue());  // Pegar cotação
            TransactionDTO transactionDTO = storeService.processSell(productId); // Processar compra
            fidelityService.processBonus(userId, total); // Processa bonus

            return transactionDTO;
        } else {
            return createSellFall(productId,  userId, ft);
        }
    }

    public TransactionDTO createSellFall(long productId, long userId, boolean ft) {
        try {
            ProductDTO product = storeClient.getProduct(productId); // Detalhes do produto
            ExchangeDTO exchange = exchangeClient.getExchange(); // Pegar cotação
            TransactionDTO transactionDTO = storeClient.createSell(productId); // Processar compra
            double total = exchange.getRate() * product.getValue();
            fidelityClient.sendBonus(userId, total); // Criar bonus

            return transactionDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private double conversion(double value)  {
        double rate = exchangeService.getRate();
        return value * rate;
    }
}
