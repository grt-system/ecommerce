package com.imd.ecommerce.consumer;


import com.imd.ecommerce.client.Fidelity;
import com.imd.ecommerce.dto.FidelityDTO;
import com.imd.ecommerce.service.FidelityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FidelityConsumer {


    @Autowired
    private Fidelity fidelityClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(FidelityConsumer.class);

    @RabbitListener(queues = "bonusQueue")
    public void receiveBonusRequest(FidelityDTO request) {
        try {
            fidelityClient.sendBonus(request.getUserId(), request.getBonus());
        } catch (Exception e) {
            logger.error("Erro ao processar bônus, reencaminhando...", e);
        }
    }
}