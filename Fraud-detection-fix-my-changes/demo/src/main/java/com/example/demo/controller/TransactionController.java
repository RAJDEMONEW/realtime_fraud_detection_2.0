
package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;
 private final TransactionRepository transactionRepository;
    @Autowired
    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

   

    @GetMapping("/gettransactions")
    public String getAllTransactions(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

      @PostMapping("/createtransaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @GetMapping("/searchtranactions")
    public String searchTransactionsByUserId(@RequestParam("userId") String userId, Model model) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
}