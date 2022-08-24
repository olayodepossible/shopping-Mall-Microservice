package com.possible.customerservice.controller;


import com.possible.customerservice.service.CustomerService;
import com.possible.customerservice.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public Customer saveCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    @PostMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.updateCustomer(customer));

    }
    @GetMapping("/delete{customerId}")
    public  void deleteCustomer(@RequestParam String customerId){

        customerService.deleteCustomer(customerId);

    }
    @GetMapping("/find/{customerId}")
    public ResponseEntity<Customer> findById(@RequestParam  String customerId)
    {
        return ResponseEntity.ok(customerService.findById(customerId).orElse(null));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll(){
        return ResponseEntity.ok(new ArrayList<>(customerService.findAll()));
    }
}
