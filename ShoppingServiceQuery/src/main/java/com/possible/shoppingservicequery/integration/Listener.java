package com.possible.shoppingservicequery.integration;

import com.possible.shoppingservicequery.model.ShoppingCart;
import com.possible.shoppingservicequery.service.ShoppingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class Listener {

    @Autowired
    private ShoppingService shoppingService;


    @KafkaListener(topics = {"shoppingCommand"})
    public void handleListener(@Payload String messageString){
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("Kafka Message in ShoppingService: {}", messageString);

        try {
          Message message = objectMapper.readValue(messageString , Message.class);


            if(message.getCommand().equals("addCart")){
                String customerId = (String) message.getMessage();
                ShoppingCart cart = new ShoppingCart();
                cart.setCustomerId(customerId);
                shoppingService.addShoppingCart(cart);
            }
             if(message.getCommand().equals("addProductAndQuantity")){
                 Message<CustomerProductQuantityDTO> messageAddProductAndQuality = objectMapper.readValue(messageString  ,
                         new TypeReference<>() {
                         }
                 );

                 CustomerProductQuantityDTO customerProductQuantityDTO1 = messageAddProductAndQuality.getMessage();
                shoppingService.addProductToAShoppingCart(
                        customerProductQuantityDTO1.getCustomerId(),
                        customerProductQuantityDTO1.getProduct(),
                        customerProductQuantityDTO1.getQuantity());
            }
            else if(message.getCommand().equals("removeProductWithQuality")){
                 Message<CustomerProductQuantityDTO> messageRemoveProductWithQuality = objectMapper.readValue(messageString  ,
                         new TypeReference<>() {
                         }
                 );
                CustomerProductQuantityDTO customerProductQuantityDTO2 =
                        (CustomerProductQuantityDTO) messageRemoveProductWithQuality.getMessage();

                shoppingService.removeProductWithQuantity(
                        customerProductQuantityDTO2.getCustomerId(),
                        customerProductQuantityDTO2.getProduct(),
                        customerProductQuantityDTO2.getQuantity());

            }
            else if(message.getCommand().equals("removeAllProduct")){
                 Message<CustomerProductDTO> messageRemoveAllProduct = objectMapper.readValue(messageString  ,
                         new TypeReference<Message<CustomerProductDTO>>() {}
                 );

                CustomerProductDTO customerProductDTO =  messageRemoveAllProduct.getMessage();
                shoppingService.removeAllProduct(
                        customerProductDTO.getCustomerId(),
                        customerProductDTO.getProduct());
            }

             else if(message.getCommand().equals("checkout")){

                 shoppingService.removeCartLine((String) message.getMessage());
             }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
