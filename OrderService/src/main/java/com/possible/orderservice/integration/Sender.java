package com.possible.orderservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Sender {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(Message message){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String messageString = objectMapper.writeValueAsString(message);
            log.info("Message we are sending to ProductService is: {}", messageString);
            kafkaTemplate.send("placeOrderTopic" , messageString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
