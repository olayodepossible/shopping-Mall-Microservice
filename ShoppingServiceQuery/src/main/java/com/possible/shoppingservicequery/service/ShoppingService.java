package com.possible.shoppingservicequery.service;

import com.possible.shoppingservicequery.model.CartLine;
import com.possible.shoppingservicequery.model.Product;
import com.possible.shoppingservicequery.model.ShoppingCart;
import com.possible.shoppingservicequery.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    public void addShoppingCart(ShoppingCart shoppingCart){
        shoppingRepository.save(shoppingCart);
    }

    public void addProductToAShoppingCart(String customerId, Product product, Integer quantity){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());

        shoppingCart.addProduct(product,quantity);
        shoppingRepository.save(shoppingCart);
    }

    public void removeProductWithQuantity(String customerId , Product product , Integer quantity){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());

        shoppingCart.removeProduct(product,quantity);
        shoppingRepository.save(shoppingCart);
    }

    public void removeAllProduct(String customerId , Product product ){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());

        CartLine cartLine = null;

        for(CartLine cartLine2 : shoppingCart.getCartLineList()){
            if(cartLine2.getProduct().equals(product)){
                cartLine = cartLine2;
            }
        }

        shoppingCart.removeAllProduct(cartLine);

        shoppingRepository.save(shoppingCart);
    }

    public List<CartLine> viewShoppingCart(String customerId){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());
        return shoppingCart.viewShoppingCart();

    }

    public void removeCartLine(String customerId){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());
        shoppingCart.removeCartLineList();
        shoppingRepository.save(shoppingCart);
    }

}
