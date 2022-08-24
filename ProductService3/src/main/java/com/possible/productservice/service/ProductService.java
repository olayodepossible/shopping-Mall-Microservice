package com.possible.productservice.service;

import com.possible.productservice.domain.OrderLines;
import com.possible.productservice.domain.Product;
import com.possible.productservice.domain.Products;

public interface ProductService {

    Products getAllProducts();
    Product getProductById(String productNumber);
    Product addProduct(Product product);
    void deleteProductById(String productNumber);
    Product editProduct(String productNumber, Product product);

    Integer getProductNumInStock(String productNumber);
    Product addProductToStock(String productNumber, Integer quantity);
    Product removeProductFromStock(String productNumber, Integer quantity);
    void removeQuantityOfProducts(OrderLines orderLines);


}
