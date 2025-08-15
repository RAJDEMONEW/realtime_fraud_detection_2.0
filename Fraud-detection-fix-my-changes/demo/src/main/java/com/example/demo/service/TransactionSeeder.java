package com.example.demo.service;

import com.mongodb.client.MongoCollection;
import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionSeeder {
    private static final Logger logger = LoggerFactory.getLogger(TransactionSeeder.class);
    private final CustomerRepository customerRepository;
    private final EmbeddingGenerator embeddingGenerator;
    private final TransactionRepository transactionRepository;
    private final TransactionChangeStreamListener transactionChangeStreamListener;
    private final MongoCollection<Document> transactionsCollection;

    public TransactionSeeder(CustomerRepository customerRepository,
                             EmbeddingGenerator embeddingGenerator, TransactionRepository transactionRepository,
                             TransactionChangeStreamListener transactionChangeStreamListener, MongoCollection<Document> transactionsCollection) {
        this.transactionsCollection = transactionsCollection;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.embeddingGenerator = embeddingGenerator;
        this.transactionChangeStreamListener = transactionChangeStreamListener;
    }
  

 @PostConstruct
    public void seedTransactions() throws IOException {
        if (transactionsCollection.countDocuments() > 0) {
            logger.info("Transactions already seeded.");
            return;
        }

        List<Customer> customers = customerRepository.findAll();
        List<Transaction> transactions = new ArrayList<>();

        for (Customer customer : customers) {
            for (int i = 0; i < 10; i++) {
                Transaction transaction = Transaction.generateRandom(customer);

                  String embeddingText = transaction.generateEmbeddingText();
                float[] embedding = embeddingGenerator.getEmbedding(embeddingText);
                transaction.setEmbedding(embedding);
                transactions.add(transaction);
            }
        }

        transactionRepository.saveAll(transactions);
        logger.info("Seeded 100 transactions.");

        transactionChangeStreamListener.startListening();
        logger.info("Change Stream Listener Started.");
    }

   
}
