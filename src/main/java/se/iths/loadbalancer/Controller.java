package se.iths.loadbalancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.beans.BeanProperty;
@RestController
@EnableRetry
public class Controller {

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/countries")
    @Retryable(value = RemoteAccessException.class,
            maxAttempts = 2, backoff = @Backoff(delay = 100))
    public String countries() {

        return this.restTemplate.getForObject("http://countries-service/countries/", String.class);
    }
    @GetMapping("/songs")
    @Retryable(value = RemoteAccessException.class,
            maxAttempts = 2, backoff = @Backoff(delay = 100))
    public String songs() {

        return this.restTemplate.getForObject("http://songs/songs/", String.class);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}