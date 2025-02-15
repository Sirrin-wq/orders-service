package org.example.ordersservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This class is responsible for configuring the application's beans.
 */
@Configuration
public class AppConfig {

    /**
     * Creates and returns a new instance of {@link RestTemplate}.
     *
     * <p>The {@link RestTemplate} is a convenient utility for making HTTP requests.
     * It simplifies the process of performing GET, POST, PUT, DELETE, and other HTTP operations.
     *
     * @return A new instance of {@link RestTemplate}.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
