package com.possible.shoppingservice.service;

import com.possible.shoppingservice.integration.CustomerProductDTO;
import com.possible.shoppingservice.integration.CustomerProductQuantityDTO;
import com.possible.shoppingservice.integration.Message;
import com.possible.shoppingservice.integration.Sender;
import com.possible.shoppingservice.model.CartLine;
import com.possible.shoppingservice.model.Product;
import com.possible.shoppingservice.model.ShoppingCart;
import com.possible.shoppingservice.repository.ShoppingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private Sender sender;


    public ShoppingCart addShoppingCart(String customerId){
        Message<String> message = new Message<>("addCart", customerId);
        ShoppingCart cart = new ShoppingCart();
        cart.setCustomerId(customerId);
       ShoppingCart shoppingCart = shoppingRepository.save(cart);

        sender.send(message);
        return shoppingCart;
    }

    public Product addProductToAShoppingCart(String customerId, Product product, Integer quantity){
        Message<CustomerProductQuantityDTO> customerProductQualityDTOMessage =
                new Message<CustomerProductQuantityDTO>(
                        "addProductAndQuantity",
                        new CustomerProductQuantityDTO(
                        customerId,
                        product,
                        quantity)
                );
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();
        List<CartLine> cartLineList = shoppingCart.getCartLineList();

        for(CartLine cartLine  : cartLineList){
            if(cartLine.getProduct().equals(product)){
                cartLine.changeQuantity(cartLine.getQuantity() + quantity);
                return cartLine.getProduct();
            }
        }
        cartLineList.add(new CartLine(product,quantity));

        log.info("*************shoppingCart IN SHOPINGSERVICE*******{}", shoppingCart);
        shoppingRepository.save(shoppingCart);

        sender.send(customerProductQualityDTOMessage);
        return product;

    }

    public boolean removeProductWithQuantity(String customerId , String productId , Integer quantity){

        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();
        List<CartLine> cartLineList = shoppingCart.getCartLineList();
        Product product = null;
        for(CartLine cartLine : cartLineList){

            if(cartLine.getProduct().getProductId().equals(productId)){
                if( cartLine.getQuantity() > quantity ){
                    cartLine.changeQuantity(cartLine.getQuantity() - quantity);
                    product = cartLine.getProduct();
                    return true;
                }
                else if(cartLine.getQuantity() <= quantity ){
                    cartLineList.remove(cartLine);
                    return false;
                }

            cartLineList.remove(cartLine);
            }
        }
        Message<CustomerProductQuantityDTO> customerProductQualityDTOMessage =
                new Message<>(
                        "removeProductWithQuality",
                        new CustomerProductQuantityDTO(customerId,product,quantity)
                );

        shoppingRepository.save(shoppingCart);
        sender.send(customerProductQualityDTOMessage);
        return true;
    }

    public void removeAllProduct(String customerId , String productId ){

        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();

        for(CartLine cartLine : shoppingCart.getCartLineList()){

            if(cartLine.getProduct().getProductId().equals(productId)){

                shoppingCart.getCartLineList().remove(cartLine);
               log.info("Shopping cart in SHOPPING SERVICE{}", shoppingCart);
                Message<CustomerProductDTO> customerProductQualityDTOMessage =
                        new Message<>(
                                "removeAllProduct",
                                new CustomerProductDTO( customerId,cartLine.getProduct())
                        );
                shoppingRepository.save(shoppingCart);
                sender.send(customerProductQualityDTOMessage);
                return;

            }

        }
    }

    public List<CartLine> checkoutCart(String customerId){

        Message<String> message = new Message<>("checkout",customerId);

        ShoppingCart cart = shoppingRepository.findByCustomerId(customerId).orElseThrow();

        sender.send(message);
        log.info("This is the list of CartLines : {}", cart.getCartLineList());

        return cart.getCartLineList();
    }

    public void removeCartLine(String customerId){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();
        shoppingCart.setCartLineList(new ArrayList<>());
        shoppingRepository.save(shoppingCart);
    }


}
