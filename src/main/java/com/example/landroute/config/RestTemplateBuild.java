package com.example.landroute.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateBuild {
     @Bean
     public RestTemplate restTemplate(){
         return new org.springframework.boot.web.client.RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(2000)).setReadTimeout(Duration.ofMillis(2000)).build();
     }
}