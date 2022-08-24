package com.possible.productservice.service.impl;

import com.possible.productservice.domain.OrderLines;
import com.possible.productservice.exception.ProductNotfoundException;
import com.possible.productservice.domain.Product;
import com.possible.productservice.domain.Products;
import com.possible.productservice.repository.ProductRepository;
import com.possible.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Products getAllProducts() {
        return new Products(productRepository.findAll());
    }

    @Override
    public Product getProductById(String productNumber) {
        return productRepository.findById(productNumber).orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(String productNumber) {
        Product product= productRepository.findById(productNumber).orElse(null);
        if (product==null) throw new ProductNotfoundException("Product not found"); // throw an exception (product not found)
        productRepository.deleteById(productNumber);
    }

    @Override
    public Product editProduct(String productNumber, Product product) {
        Product oldProduct= productRepository.findById(productNumber).orElse(null);
        if (oldProduct==null) throw new ProductNotfoundException("Product not found"); //throw an exception
        oldProduct.setProductName(product.getProductName());
        oldProduct.setProductPrice(product.getProductPrice());
        oldProduct.setProductDescription(product.getProductDescription());
        oldProduct.setProductNumInStock(product.getProductNumInStock());
        return productRepository.save(oldProduct);

    }

    @Override
    public Integer getProductNumInStock(String productNumber) {
        Product product= productRepository.findById(productNumber).orElse(null);
        if(product==null) return 0; //handle exception
        return product.getProductNumInStock();
    }

    @Override
    public Product addProductToStock(String productNumber, Integer quantity) {
        Product product= productRepository.findById(productNumber).orElse(null);
        if(product==null) throw new ProductNotfoundException("Product not found"); //handle exception
        product.setProductNumInStock(product.getProductNumInStock()+quantity);
        return productRepository.save(product);
    }

    @Override
    public Product removeProductFromStock(String productNumber, Integer quantity) {
        Product product= productRepository.findById(productNumber).orElse(null);
        if(product==null) return null; //handle exception
        Integer productNumInStock= product.getProductNumInStock();
        if (quantity> productNumInStock) throw new ProductNotfoundException("Not enough products in stock"); //throw an exception
        product.setProductNumInStock(productNumInStock-quantity);
        //if product number in stock is 0, delete the product
        if(product.getProductNumInStock()==0) {
            productRepository.delete(product);
            return product;
        }
        return productRepository.save(product);
    }

    @Override
    public void removeQuantityOfProducts(OrderLines orderLines) {
        if (orderLines==null) return;
        orderLines.getOrderLineList().stream().forEach(orderLine->{
            removeProductFromStock(orderLine.getProduct().getProductNumber(), orderLine.getQuantity());
        });
    }
}
