package com.possible.orderservice.controller;

import com.possible.orderservice.model.CartLine;
import com.possible.orderservice.model.Customer;
import com.possible.orderservice.model.Order;
import com.possible.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/placeOrder/orderNumber/{orderNumber}")
    public void placeOrder(@PathVariable String orderNumber , @RequestBody Customer customer){

        orderService.placeOrder(orderNumber , customer);


    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<Order>> getOrders(){
        return new ResponseEntity<>(orderService.getOrders(),HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody List<CartLine> cartLines){
        Order order = orderService.createOrder(cartLines);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
}
