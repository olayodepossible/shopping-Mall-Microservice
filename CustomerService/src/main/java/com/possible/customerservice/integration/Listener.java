package com.possible.customerservice.integration;

import com.possible.customerservice.domain.Customer;
import com.possible.customerservice.domain.Order;
import com.possible.customerservice.domain.OrderLine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class Listener {

    @KafkaListener(topics = {"placeOrderTopic"})
    public void receive(String messageString){
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Kafka Message received by CustomerService {}", messageString);
        try {

            Message message = objectMapper.readValue(messageString, Message.class);
            if(message.getCommand().equals("customerService")){

                Message<Order> messageAddProductAndQuality = objectMapper.readValue(
                        messageString  ,
                        new TypeReference<>() {
                        });

                Customer customer = messageAddProductAndQuality.getMessage().getCustomer();
                List<OrderLine> orderLines = messageAddProductAndQuality.getMessage().getOrderLineList();
                log.info("Order placed for this customer ={} is {}", customer, orderLines );

            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
