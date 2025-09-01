package com.example.demo.service;

import com.example.demo.controller.websocketController;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

@Service
public class TransactionChangeStreamListener {

    private static final Logger logger = LoggerFactory.getLogger(TransactionChangeStreamListener.class);

    private final TransactionVectorSearchService vectorSearchService;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final MongoCollection<Document> transactionsCollection;
    private final websocketController fraudStreamController;


    public TransactionChangeStreamListener(TransactionVectorSearchService vectorSearchService,
                                           MongoCollection<Document> transactionsCollection,websocketController fraudStreamController) {
        this.vectorSearchService = vectorSearchService;
        this.transactionsCollection = transactionsCollection;
        this.fraudStreamController = fraudStreamController;
    }

    @PostConstruct
    public void startListening() {
        logger.info("✅ startListening() called. Starting change stream listener thread...");

        executorService.submit(() -> {
            logger.info("🟢 MongoDB change stream background thread started.");

            List<Bson> pipeline = List.of(
                Aggregates.match(Filters.eq("operationType", "insert"))
            );

            try (MongoCursor<ChangeStreamDocument<Document>> cursor = transactionsCollection
                    .watch(pipeline)
                    .iterator()) {

                logger.info("🔍 Now watching for new insertions in 'transactions' collection...");

                while (cursor.hasNext()) {
                    try {
                        ChangeStreamDocument<Document> change = cursor.next();
                        Document transactionDoc = change.getFullDocument();

                        if (transactionDoc != null) {
                            logger.info("🟡 New transaction detected: {}", transactionDoc);

                            List<Double> embedding = transactionDoc.getList("embedding", Double.class);
                            if (embedding != null) {
                                logger.info("🟢 Performing vector search...");
                                vectorSearchService.evaluateTransactionFraud(transactionDoc);
                            } else {
                                logger.warn("⚠️ Transaction missing 'embedding' field. Skipping.");
                            }
                        }
                    } catch (Exception processingEx) {
                        logger.error("🔥 Error while processing change stream event", processingEx);
                    }
                }

            } catch (Exception streamEx) {
                logger.error("🔥 Change stream listener failed or crashed", streamEx);
            }
        });
    }
}
