package com.rmit.apigateway.config;

import com.rmit.apigateway.exception.CustomException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
