package com.possible.orderservice.service;

import com.possible.orderservice.integration.Message;
import com.possible.orderservice.integration.Sender;
import com.possible.orderservice.model.*;
import com.possible.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Sender sender;


    public List<Order> getOrders(){
            return new ArrayList<>(orderRepository.findAll());
    }

    public Order createOrder(List<CartLine> cartLines){

        Order order = new Order();
        List<OrderLine> orderLineList = new ArrayList<>();
        for(CartLine cartLine : cartLines){
            OrderLine orderLine = OrderLine.builder().product(cartLine.getProduct()).quantity(cartLine.getQuantity()).build();
            orderLineList.add(orderLine);
        }

        order.setOrderLineList(orderLineList);
        return orderRepository.save(order);
    }

    public void placeOrder(String orderId , Customer customer){
        log.info("ORDER NUMBER ************{}", orderId);
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        log.info("1-{}", order);
        order.setCustomer(customer);
        log.info("2- After setting customer: {}", order);

        List<OrderLine> orderLines = new ArrayList<>(order.getOrderLineList());
        log.info("3-{}", order);

        orderRepository.save(order);

        Message<List<OrderLine>> messageOrderLines = new Message<>("productService" ,orderLines);

        Message<Order> messageOrder = new Message<>("customerService",order);
        log.info("1-order pass to Kafka: {}", order);

        //productService gets Updated
        sender.send(messageOrderLines);

        log.info("This is the message to be send as an Email to customer {}\n", messageOrder);

        //CustomerService receive An Email
        sender.send(messageOrder);
    }
}
