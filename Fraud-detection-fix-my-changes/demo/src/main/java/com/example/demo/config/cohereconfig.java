package com.example.demo.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
  

@Configuration

public class cohereconfig {

    
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
    
}
