package com.possible.productservice.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.possible.productservice.domain.OrderLine;
import com.possible.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderListener {

    @Autowired
    private ProductService productService;

    @KafkaListener(topics= {"placeOrderTopic"})
    public void listenWhenOrderPlaced(@Payload String orderLinesString) {


        ObjectMapper objectMapper = new ObjectMapper();
        try {

            Message message = objectMapper.readValue(orderLinesString, Message.class);
            if (message.getCommand().equals("productService")) {
                log.info("From ProductService Listen ....");

                Message<List<OrderLine>> messageOrderLines = objectMapper.readValue(
                        orderLinesString,
                        new TypeReference<>() {
                        });
                productService.removeQuantityOfProducts(messageOrderLines.getMessage());
            }
        }
            catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
