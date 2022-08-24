package com.possible.productservice.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.possible.productservice.domain.OrderLines;
import com.possible.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OrderListener {

    @Autowired
    private ProductService productService;

    @KafkaListener(topics= {"placeOrderTopic"})
    public void listenWhenOrderPlaced(@Payload String orderLinesString) {

        System.out.println("Listen ....");

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            Message message = objectMapper.readValue(orderLinesString, Message.class);
            if (message.getCommand().equals("productService")) {

                Message<OrderLines> messageOrderLines = objectMapper.readValue(
                        orderLinesString,
                        new TypeReference<Message<OrderLines>>() {
                        });
                productService.removeQuantityOfProducts(messageOrderLines.getMessage());

            }
        }
            catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
