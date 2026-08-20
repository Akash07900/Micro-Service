package com.example.orderservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfig {

    @Bean
    public CircuitBreaker productServiceCircuitBreaker() {

        io.github.resilience4j.circuitbreaker.CircuitBreakerConfig config =
                io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()

                        .failureRateThreshold(50)

                        .minimumNumberOfCalls(3)

                        .waitDurationInOpenState(
                                Duration.ofSeconds(10)
                        )

                        .permittedNumberOfCallsInHalfOpenState(1)

                        .build();

        CircuitBreaker circuitBreaker =
                CircuitBreaker.of(
                        "productService",
                        config
                );

        circuitBreaker.getEventPublisher()
                .onStateTransition(event ->
                        System.out.println(
                                "CIRCUIT BREAKER STATE: "
                                        + event.getStateTransition()
                        )
                );

        return circuitBreaker;
    }
}