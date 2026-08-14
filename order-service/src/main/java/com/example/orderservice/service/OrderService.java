package com.example.orderservice.service;

import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.exception.ProductNotFoundException;
import com.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class OrderService {

    private final RestClient.Builder restClientBuilder;
    private final String productServiceUrl;

    public OrderService(
            RestClient.Builder restClientBuilder,
            @Value("${product.service.url}") String productServiceUrl) {

        this.restClientBuilder = restClientBuilder;
        this.productServiceUrl = productServiceUrl;
    }

    public String createOrder(Order order) {

        RestClient restClient = restClientBuilder.build();

        ProductResponse product;

        try {
            product = restClient.get()
                    .uri(productServiceUrl + "/api/products/"
                            + order.getProductId())
                    .retrieve()
                    .body(ProductResponse.class);

        } catch (HttpClientErrorException.NotFound ex) {

            throw new ProductNotFoundException(
                    "Product not found with id: "
                            + order.getProductId()
            );
        }

        double totalPrice =
                product.getPrice() * order.getQuantity();

        return "Order created successfully. Product: "
                + product.getName()
                + ", Total Price: "
                + totalPrice;
    }
}