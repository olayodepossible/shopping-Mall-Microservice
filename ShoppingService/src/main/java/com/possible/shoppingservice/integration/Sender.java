package com.possible.shoppingservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    @Autowired
    private KafkaTemplate<String , String> kafkaTemplate;

    public void send(Message message)  {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String stringMessage= objectMapper.writeValueAsString(message);
            System.out.println("Sending message");
            kafkaTemplate.send("shoppingCommand",stringMessage);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
