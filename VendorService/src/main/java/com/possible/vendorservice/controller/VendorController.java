package com.possible.vendorservice.controller;


import com.possible.vendorService.service.vendorService;
import com.possible.vendorService.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/save")
    public Customer saveCustomer(@RequestBody Customer customer){
        return vendorService.saveCustomer(customer);
    }

    @PostMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok(vendorService.updateCustomer(customer));

    }
    @GetMapping("/delete{customerId}")
    public  void deleteCustomer(@RequestParam String customerId){

        vendorService.deleteCustomer(customerId);

    }
    @GetMapping("/find/{customerId}")
    public ResponseEntity<Customer> findById(@RequestParam  String customerId)
    {
        return ResponseEntity.ok(vendorService.findById(customerId).orElse(null));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll(){
        return ResponseEntity.ok(new ArrayList<>(vendorService.findAll()));
    }
}
