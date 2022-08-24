package com.possible.clientapplication;

import com.possible.clientapplication.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class ClientApplication {

//    @Autowired
//    Client client;

    private final String baseUrl = "http://localhost:8080";

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

/*    @Override
    public void run(String... args) throws Exception {

        log.info("=============Get Products=============");
        log.info("{}\n", client.getProducts());

        log.info("=============adding Product: Macbook...=============");
        Product product= new Product();
        product.setProductName("MacBook pro 11");
        product.setProductPrice(2000.0);
        product.setProductDescription("256 gb storage 16 RAM");
        product.setProductNumInStock(10);
        client.addProduct(product);

        log.info("=============adding Product: Samsung 21...=============");
        Product prod2= new Product();
        prod2.setProductName("Samsung 21");
        prod2.setProductPrice(1200.0);
        prod2.setProductDescription("128gb 8 RAM");
        prod2.setProductNumInStock(4);
        client.addProduct(prod2);

        log.info("============= Get All Products=============");
        var allProducts= client.getProducts();
        log.info("{}\n", allProducts);

        //edit product
        log.info("============= Edit Product from Macbook to Hp=============");
        Product product1 = allProducts.getProducts().get(0);
        Product product2 = allProducts.getProducts().get(1);
        product1.setProductName("Hp");
        product1.setProductPrice(1200.0);
        product1.setProductDescription("125 gb storage 16 RAM");
        product1.setProductNumInStock(5);
        client.modifyProduct(product1,product1.getProductNumber());
        var editedProduct= client.getProducts();
        log.info("*****Edited Product**********\n{}",editedProduct);


        //create and get cutomers

        log.info("============= Create Customer ... =============");
        Customer cust1= new Customer();
        cust1.setFirstName("Michale");
        cust1.setLastName("Rezene");
        cust1.setEmail("mike@gmail.com");
        cust1.setPhone("641256646");
        cust1.setAddress(new Address("1000 North st", "Fairfield", "52557"));

        restTemplate().postForLocation(baseUrl+"/customer/save", cust1, Customer.class);
        log.info("============= get Customer number one=============");
        var customers= restTemplate().getForObject(baseUrl+"/customer/findall", Customers.class);
        var customer1= customers.getCustomerList().get(0);
        log.info("Custoer{}", customer1);


        //Create shopping cart to a customer
        log.info("============= Create cart to Customer1 ...=============");
        restTemplate().postForLocation(baseUrl+"/cart/addCartForACustomer/"+ customer1.getCustomerId(), null, ShoppingCart.class);

        //add product(Hp) to cart of customer1
        log.info("============= Put product1 to cart of customer1 .... =============");
        restTemplate().postForLocation(baseUrl+"/cart/addProductToCartWithQuantity/" +customer1.getCustomerId()+"/quantity/"+ 4, product1, Products.class);
        restTemplate().postForLocation(baseUrl+"/cart/addProductToCartWithQuantity/" +customer1.getCustomerId()+"/quantity/"+ 2, product2, Products.class);

        // Show the shopping cart of customer1
        log.info("============= Get shopping cart of customer1=============");
        ShoppingCart cart= restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+customer1.getCustomerId(), ShoppingCart.class);
        log.info("{}",cart);

        // delete product1(Hp) from cart
        log.info("============= Deleting product 1(Hp) from cart of customer1..=============");
        restTemplate().delete(baseUrl+ "/cart/removeProductFromCart/"+customer1.getCustomerId()+"/product/"+product2.getProductNumber(), Void.class);

        //Change the quantity of one of the products
        int quantity2= 1;
        restTemplate().delete(baseUrl+"/cart/removeProductFromCartWithQuantity/"+customer1.getCustomerId()+"/product/"+product1.getProductNumber()+"/quantity/"+quantity2, product1, Products.class);


        log.info("Retrieve and show the shoppingcart\n{}",restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+customer1.getCustomerId(), ShoppingCart.class));

        log.info("============= Checkout and place an order =============");

        Order order = restTemplate().postForObject(baseUrl+"/cart/checkout/"+customer1.getCustomerId(),null, Order.class);
//
//        //Order placed
        log.info("============= Placing an order .... =============");
        restTemplate().postForObject(baseUrl+"/order/placeOrder/orderNumber/" + order.getOrderNumber(),customer1, Void.class);

        log.info("============= Getting shopping cart (should be empty) After placing order .... =============");
        log.info("{}",restTemplate().getForObject(baseUrl+"/cartQuery/getShoppingCart/"+ customer1.getCustomerId(), ShoppingCart.class));

        log.info("============= Get all order =============");
        log.info("{}", restTemplate().getForObject(baseUrl+"/order/getOrders", Orders.class));

    }
*/
}