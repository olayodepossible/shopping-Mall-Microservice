package com.possible.shoppingservice.service;

import com.possible.shoppingservice.integration.CustomerProductDTO;
import com.possible.shoppingservice.integration.CustomerProductQualityDTO;
import com.possible.shoppingservice.integration.Message;
import com.possible.shoppingservice.integration.Sender;
import com.possible.shoppingservice.model.CartLine;
import com.possible.shoppingservice.model.Product;
import com.possible.shoppingservice.model.ShoppingCart;
import com.possible.shoppingservice.repository.ShoppingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private Sender sender;


    public ShoppingCart addShoppingCart(String customerId){
        Message<String> message = new Message<String>(
                "addCart",
                customerId
        );
       ShoppingCart shoppingCart = shoppingRepository.save(new ShoppingCart(customerId));

        sender.send(message);
        return shoppingCart;
    }

    public Product addProductToAShoppingCart(String customerId, Product product, Integer quantity){
        Message<CustomerProductQualityDTO> customerProductQualityDTOMessage =
                new Message<CustomerProductQualityDTO>(
                        "addProductAndQuantity",
                        new CustomerProductQualityDTO(
                        customerId,
                        product,
                        quantity)
                );
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).get();

        shoppingCart.addProduct(product,quantity);
        log.info("{}", shoppingCart);
        shoppingRepository.save(shoppingCart);

        sender.send(customerProductQualityDTOMessage);
        return product;

    }

    public void removeProductWithQuantity(String customerId , String productId , Integer quantity){

        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).get();
        Product product = null;
        for(CartLine cartLine : shoppingCart.getCartLineList()){

            if(cartLine.getProduct().getProductId().equals(productId)){
                product = cartLine.getProduct();
                shoppingCart.removeProduct(cartLine.getProduct(),quantity);
            }

        }
        Message<CustomerProductQualityDTO> customerProductQualityDTOMessage =
                new Message<CustomerProductQualityDTO>(
                        "removeProductWithQuality",
                        new CustomerProductQualityDTO(
                                customerId,
                                product,
                                quantity)
                );

        shoppingRepository.save(shoppingCart);
        sender.send(customerProductQualityDTOMessage);

    }

    public void removeAllProduct(String customerId , String productId ){



        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).get();

        Product product = null;

        for(CartLine cartLine : shoppingCart.getCartLineList()){

            if(cartLine.getProduct().getProductId().equals(productId)){
                product = cartLine.getProduct();
                System.out.println();

                shoppingCart.getCartLineList().remove(cartLine);
                System.out.println("Shopping cart"+ shoppingCart);
                Message<CustomerProductDTO> customerProductQualityDTOMessage =
                        new Message<CustomerProductDTO>(
                                "removeAllProduct",
                                new CustomerProductDTO(
                                        customerId,
                                        product
                                )
                        );
                //System.out.println("Shopping cart"+ shoppingCart);
                shoppingRepository.save(shoppingCart);
                sender.send(customerProductQualityDTOMessage);
                return;

            }

        }



    }

    public List<CartLine> checkoutCart(String customerId){

        Message<String> message = new Message<String>(
                "checkout",
                customerId
        );

        ShoppingCart cart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());

        sender.send(message);
        log.info("This is the list of CartLines : {}", cart.getCartLineList());

        return cart.getCartLineList();
    }

    public void removeCartLine(String customerId){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).get();
        shoppingCart.removeCartLineList();
        shoppingRepository.save(shoppingCart);
    }

}
