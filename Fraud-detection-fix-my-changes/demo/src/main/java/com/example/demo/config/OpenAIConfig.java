// package com.example.demo.config;  
// import org.springframework.ai.embedding.EmbeddingModel;  
// import org.springframework.ai.openai.OpenAiEmbeddingModel;  
// import org.springframework.ai.openai.api.OpenAiApi;  
// import org.springframework.beans.factory.annotation.Value;  
// import org.springframework.context.annotation.Bean;  
// import org.springframework.context.annotation.Configuration;  
  
// @Configuration  
// public class OpenAIConfig {  
  
//     @Value("${spring.ai.openai.api-key}")  
//     private String apiKey;  
  
// @Bean
// public EmbeddingModel embeddingModel() {
//     OpenAiApi openAiApi = OpenAiApi.builder()
//         .apiKey(apiKey)
//         .build();
//     return new OpenAiEmbeddingModel(openAiApi);
// }

// }