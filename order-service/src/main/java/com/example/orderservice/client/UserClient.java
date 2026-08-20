package com.example.orderservice.client;

import com.example.orderservice.dto.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserClient {

    private final RestClient restClient;

    public UserClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("http://localhost:8084")
                .build();
    }

    public UserResponse getUser(Long id) {

        return restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(UserResponse.class);
    }
}