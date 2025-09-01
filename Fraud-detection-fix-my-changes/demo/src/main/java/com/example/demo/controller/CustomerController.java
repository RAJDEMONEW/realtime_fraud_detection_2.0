package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
public class CustomerController {
   
    private final CustomerRepository customerRepository;
     @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/createuser")
    public Customer createUser(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/getusers")
    public List<Customer> getAllUsers() {
        return customerRepository.findAll();
    }
}
