package com.possible.shoppingservicequery.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String productId;
    private String productLogo;
    private String productName;
    private Flavour productFlavour;
    private Double productPrice;
    private Vendor vendor;
    private String productDescription;
    private Integer productNumInStock;
    private int rating;

}