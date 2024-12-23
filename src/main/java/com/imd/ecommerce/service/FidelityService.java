package com.imd.ecommerce.service;

import com.imd.ecommerce.client.Fidelity;
import com.imd.ecommerce.dto.FidelityDTO;
import io.github.resilience4j.retry.annotation.Retry;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.BackgroundJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FidelityService {

    @Autowired
    private Fidelity fidelityClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(FidelityService.class);


    @Retry(name = "fidelity", fallbackMethod = "queueFallback")
    public void processBonus(long userId, double value){
        fidelityClient.sendBonus(userId, value);
    }

    public void queueFallback(long userId, double value, Throwable t) {
        FidelityDTO request = new FidelityDTO(userId, value);
        logger.error("Failed to process bonuses for user {}, reprocessing in the background", userId, t);
        rabbitTemplate.convertAndSend("bonusQueue", request);
    }
}
