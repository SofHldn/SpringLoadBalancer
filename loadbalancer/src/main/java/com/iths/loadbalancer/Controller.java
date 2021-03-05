package com.iths.loadbalancer;

import org.springframework.beans.factory.annotation.Autowired;

import java.beans.BeanProperty;

@RestController
@EnableRetry
public class Controller {

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/getpersons")
    @Retryable(value = RemoteAccessException.class,
            maxAttempts = 2, backoff = @Backoff(delay = 100))
    public String persons() {

        return this.restTemplate.getForObject("http://persons-service/persons/", String.class);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
