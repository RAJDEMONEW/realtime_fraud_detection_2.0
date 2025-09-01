package com.example.demo.repository;  
  
import com.example.demo.model.Transaction;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;  
  
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findByUserId(String userId);  
}