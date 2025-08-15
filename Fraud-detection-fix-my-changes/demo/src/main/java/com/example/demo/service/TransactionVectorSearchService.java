package com.example.demo.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
// import com.mongodb.client.model.search.SearchPath;
import com.mongodb.client.model.search.VectorSearchOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.mongodb.client.model.search.SearchPath.fieldPath;

// import static com.mongodb.client.model.search.SearchOperator.vectorSearch;

@Service
public class TransactionVectorSearchService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionVectorSearchService.class);

    private final MongoCollection<Document> transactionCollection;

    private static final String VECTOR_INDEX_NAME = "vector_index"; // must match Atlas vector index name
    private static final int SEARCH_LIMIT = 1;
    private static final int NUM_CANDIDATES = 5;

    public TransactionVectorSearchService(MongoCollection<Document> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    public void evaluateTransactionFraud(Document transactionDoc) {  
        String transactionId = transactionDoc.getString("transactionId");  
        String userId = transactionDoc.getString("userId");  
        List<Double> embedding = transactionDoc.getList("embedding", Double.class);  
  
        // Run vector search to find similar transactions  
        List<Document> similarTransactions = findSimilarTransactions(embedding, userId);  

  
        // If no similar transactions exist for this user OR any of them are fraud -> Mark as fraud  
        boolean isFraud = similarTransactions.size()==1 ||  
                similarTransactions.stream().anyMatch(doc -> doc.getBoolean("isFraud", false)); 
                
                
                logger.info("similar transaction fucniton ran successfully :",userId ,"  :",isFraud,transactionDoc,similarTransactions);  
  
        if (isFraud) {  
            markTransactionAsFraud(transactionId);  

           
        }  

        

    }  
    List<Document> findSimilarTransactions(List<Double> embedding, String userId) {  
        Bson vectorSearch = Aggregates.vectorSearch(  
                fieldPath("embedding"),  
                embedding,  
                VECTOR_INDEX_NAME,  
                SEARCH_LIMIT,  
                VectorSearchOptions.approximateVectorSearchOptions(NUM_CANDIDATES)  
        );  
  
        Bson matchUser = Aggregates.match(Filters.eq("userId", userId));  
   logger.info("similar transactions of user x",  transactionCollection.aggregate(Arrays.asList(vectorSearch, matchUser))  
   .into(new ArrayList<>()) );  
        return transactionCollection.aggregate(Arrays.asList(vectorSearch, matchUser))  
                .into(new ArrayList<>());  
    } 
       private void markTransactionAsFraud(String transactionId) {  
        transactionCollection.updateOne(  
                Filters.eq("transactionId", transactionId),  
                Updates.set("isFraud", true)  
        );  
        logger.info("Transaction marked as fraud: {}", transactionId);  
    } 

}
