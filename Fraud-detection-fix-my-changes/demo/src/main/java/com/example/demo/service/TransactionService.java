package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// @Service
// public class TransactionService {

//     private final List<Transaction> transactions; // This should be replaced with actual database access

//     @Autowired
//     public TransactionService(List<Transaction> transactions) {
//         this.transactions = transactions;
//     }

//     public List<Transaction> getAllTransactions() {
//         return transactions;
//     }

//     public List<Transaction> getTransactionsByUserId(String userId) {
//         return transactions.stream()
//                 .filter(transaction -> transaction.getUserId().equals(userId))
//                 .collect(Collectors.toList());
//     }
// }



@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactionRepository.findByUserId(userId);
    }
}
