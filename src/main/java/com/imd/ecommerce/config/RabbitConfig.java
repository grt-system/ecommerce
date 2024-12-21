package com.imd.ecommerce.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final String MAIN_QUEUE = "bonusQueue";
    private static final String DLQ = "deadLetterQueue";
    private static final String DLX_EXCHANGE = "deadLetterExchange";

//    @Bean
//    public Queue bonusQueue() {
//        return new Queue("bonusQueue", true);
//    }

        @Bean
        public Queue mainQueue() {
            return QueueBuilder.durable(MAIN_QUEUE)
                    .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                    .withArgument("x-dead-letter-routing-key", DLQ)
                    .build();
        }

        @Bean
        public Queue deadLetterQueue() {
            return QueueBuilder.durable(DLQ)
                    .withArgument("x-message-ttl", 600)
                    .withArgument("x-dead-letter-exchange", MAIN_QUEUE)
                    .withArgument("x-dead-letter-routing-key", MAIN_QUEUE)
                    .build();
        }

        @Bean
        public DirectExchange deadLetterExchange() {
            return new DirectExchange(DLX_EXCHANGE);
        }

        @Bean
        public Binding dlqBinding() {
            return BindingBuilder.bind(deadLetterQueue())
                    .to(deadLetterExchange())
                    .with(DLQ);
        }

        @Bean
        public Binding mainQueueBinding(Queue mainQueue, DirectExchange deadLetterExchange) {
            return BindingBuilder.bind(mainQueue)
                    .to(deadLetterExchange)
                    .with(MAIN_QUEUE);
        }
}
