package com.possible.vendorservice.integration;

import com.possible.vendorservice.domain.Customer;
import com.possible.vendorservice.domain.Order;
import com.possible.vendorservice.domain.OrderLine;
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
        log.info("Kafka Message received by VendorService {}", messageString);
        try {

            Message message = objectMapper.readValue(messageString, Message.class);
            if(message.getCommand().equals("vendorService")){

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
