package org.example.automanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class AutoManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoManagerApplication.class, args);
    }

    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }

    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }
}
