package com.possible.clientapplication.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    private String customerId;
    private List<CartLine> cartLineList = new ArrayList<>();

    public ShoppingCart(String customerId) {
        this.customerId = customerId;
    }

    public boolean addProduct(Product product, Integer quantity){
        for(CartLine cartLine  : cartLineList){
            if(cartLine.getProduct().equals(product)){
                cartLine.changeQuantity(cartLine.getQuantity() + quantity);
                return true;
            }
        }
        cartLineList.add(new CartLine(product,quantity));
        return true;
    }

    public boolean removeProduct(Product product,Integer quantity){
        for(CartLine cartLine : cartLineList){
            if(cartLine.getProduct().equals(product) && cartLine.getQuantity() > quantity ){
                cartLine.changeQuantity(cartLine.getQuantity() - quantity);
                return true;
            }
            else if(cartLine.getProduct().equals(product) && cartLine.getQuantity() <= quantity ){
                cartLineList.remove(product);
                return false;
            }
        }
        cartLineList.remove(product);
        return true;
    }

    public void removeAllProduct(Product product){
        cartLineList.remove(product);
    }

    public List<CartLine> checkout(){
        return this.cartLineList;
    }

    public void removeCartLineList(){
        this.cartLineList = new ArrayList<>();
    }

}
