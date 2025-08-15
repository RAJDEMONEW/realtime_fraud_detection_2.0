package com.example.demo.service;

import java.io.IOException;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



import com.google.gson.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

// @Component
// public class EmbeddingGenerator {
//     private final EmbeddingModel embeddingModel;

//     public EmbeddingGenerator(EmbeddingModel embeddingModel) {
//         this.embeddingModel = embeddingModel;
//     }

//     public float[] getEmbedding(String transaction) {
//         float[] embeddingResponse = this.embeddingModel.embed(transaction);
//         return embeddingResponse;
//     }
// }



// public class EmbeddingGenerator {

//     @Value("${cohere.api.key}")
//     private String apiKey;

//     private final OkHttpClient client;

//     public EmbeddingGenerator(OkHttpClient client) {
//         this.client = client;
//     }

//     public float[] getEmbedding(String text) throws IOException {
//         String url = "https://api.cohere.ai/v1/embed";

//         JsonObject json = new JsonObject();
//         json.add("texts", new Gson().toJsonTree(Collections.singletonList(text)));
//         json.addProperty("model", "embed-english-v3.0");
//         json.addProperty("input_type", "search_document");

//         RequestBody body = RequestBody.create(
//             json.toString(),
//             MediaType.parse("application/json")
//         );

//         Request request = new Request.Builder()
//                 .url(url)
//                 .post(body)
//                 .addHeader("Authorization", "Bearer " + apiKey)
//                 .addHeader("Content-Type", "application/json")
//                 .build();

//         try (Response response = client.newCall(request).execute()) {
//             if (!response.isSuccessful()) {
//                 throw new IOException("Cohere API call failed: " + response);
//             }

//             JsonObject jsonResponse = JsonParser.parseString(response.body().string()).getAsJsonObject();
//             JsonArray embeddingsArray = jsonResponse.getAsJsonArray("embeddings").get(0).getAsJsonArray();

//             List<Double> embedding = new ArrayList<>();
//             for (JsonElement e : embeddingsArray) {
//                 embedding.add(e.getAsDouble());
//             }

//               float[] embeddingArray = new float[embedding.size()];
//         for (int i = 0; i < embedding.size(); i++) {
//             embeddingArray[i] = embedding.get(i).floatValue();
//         }

//         return embeddingArray;

//         }
//     }


// }

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class EmbeddingGenerator {

    private final WebClient webClient;

    public EmbeddingGenerator() {
        this.webClient = WebClient.create("http://localhost:8000");
    }

    /**
     * Sends a POST request to the embedding API with the input text
     * and retrieves the embedding vector.
     *
     * @param text Input text to embed
     * @return List of float values representing the embedding
     */
public float[] getEmbedding(String text) {
    Map<String, String> request = Map.of("text", text);

    Map<String, List<Float>> response = webClient.post()
            .uri("/embed")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, List<Float>>>() {})
            .block();

    if (response == null || !response.containsKey("embedding")) {
        throw new RuntimeException("Failed to fetch embedding from embedding service");
    }

    List<Float> embedding = response.get("embedding");

    float[] embeddingArray = new float[embedding.size()];
    for (int i = 0; i < embedding.size(); i++) {
        embeddingArray[i] = embedding.get(i);
    }

    return embeddingArray;
}

}
