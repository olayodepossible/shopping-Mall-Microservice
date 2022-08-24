package com.possible.productservice.controller;


import com.possible.productservice.domain.Product;
import com.possible.productservice.domain.Products;
import com.possible.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger logger =
            LoggerFactory.getLogger(ProductController.class.getName());

    @GetMapping
    public ResponseEntity<Products> getAllProducts(){
        logger.info("Calling get all Products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productNumber}")
    public ResponseEntity<Product> getProductById(@PathVariable("productNumber") String productNumber){
        logger.info("Calling get product by id");
        return ResponseEntity.ok(productService.getProductById(productNumber));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        logger.info("Calling add product by id");
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @DeleteMapping("/{productNumber}")
    public void deleteProductById(@PathVariable("productNumber") String productNumber){
        logger.info("Calling delete product by id");
        productService.deleteProductById(productNumber);
    }

    @PutMapping("/{productNumber}")
    public ResponseEntity<Product> editProduct(@PathVariable String productNumber,
                                               @RequestBody Product product){
        logger.info("Calling edit product by id");
        return ResponseEntity.ok(productService.editProduct(productNumber, product));
    }

    @GetMapping("/{productNumber}/isInStock")
    public ResponseEntity<Boolean> productIsInStock(@PathVariable String productNumber){
        logger.info("Calling get product in stock");
        if (productService.getProductNumInStock(productNumber)>0) return ResponseEntity.ok(true);
        return ResponseEntity.ok(false);
    }

//    @GetMapping("/numInStock/{productNumber}")
//    public ResponseEntity<Integer> getProductNumInStock(@PathVariable String productNumber){
//        return ResponseEntity.ok(productService.getProductNumInStock(productNumber));
//    }
//    @PutMapping("/changeQuantity")
//    public void removeProductFromStock(@RequestBody OrderLines orderLines){
//        productService.removeQuantityOfProducts(orderLines);
//    }

}
