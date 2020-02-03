package TP.config;

import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
