package com.possible.productservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Product {
    @Id
    private String productNumber;
    private String productName;
    private Double productPrice;
    private String productDescription;
    private Integer productNumInStock;


    // remove product from stock
    public void removeProductFromStock(int quantity){
        if (quantity> productNumInStock) return; //thorw an exception
        productNumInStock= productNumInStock-quantity;
    }

}
