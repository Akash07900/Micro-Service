package com.example.orderservice.service;

import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.exception.ProductNotFoundException;
import com.example.orderservice.model.Order;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.function.Supplier;

@Service
public class OrderService {

    private final RestClient.Builder restClientBuilder;
    private final String productServiceUrl;
    private final CircuitBreaker productServiceCircuitBreaker;

    public OrderService(
            RestClient.Builder restClientBuilder,
            @Value("${product.service.url}") String productServiceUrl,
            CircuitBreaker productServiceCircuitBreaker) {

        this.restClientBuilder = restClientBuilder;
        this.productServiceUrl = productServiceUrl;
        this.productServiceCircuitBreaker = productServiceCircuitBreaker;
    }

    public String createOrder(Order order) {

        RestClient restClient = restClientBuilder.build();

        Supplier<ProductResponse> productCall = () -> {

            System.out.println("Calling Product Service...");

            return restClient.get()
                    .uri(productServiceUrl + "/api/products/"
                            + order.getProductId())
                    .retrieve()
                    .body(ProductResponse.class);
        };

        try {

            ProductResponse product =
                    productServiceCircuitBreaker.executeSupplier(
                            productCall
                    );

            double totalPrice =
                    product.getPrice() * order.getQuantity();

            return "Order created successfully. Product: "
                    + product.getName()
                    + ", Total Price: "
                    + totalPrice;

        } catch (HttpClientErrorException.NotFound ex) {

            throw new ProductNotFoundException(
                    "Product not found with id: "
                            + order.getProductId()
            );

        } catch (Exception ex) {

            return "Product Service is currently unavailable. "
                    + "Please try again later.";
        }
    }
}