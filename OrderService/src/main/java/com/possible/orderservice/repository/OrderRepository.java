package com.possible.orderservice.repository;

import com.possible.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {


    Optional<Order> findByOrderId(String orderId);
}
