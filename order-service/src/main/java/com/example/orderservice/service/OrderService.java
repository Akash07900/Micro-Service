package com.example.orderservice.service;

import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OrderService {

    private final RestClient restClient;

    public OrderService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String createOrder(Order order) {

        ProductResponse product = restClient.get()
                .uri("http://localhost:8082/api/products/" + order.getProductId())
                .retrieve()
                .body(ProductResponse.class);

        if (product == null) {
            return "Product not found";
        }

        double totalPrice =
                product.getPrice() * order.getQuantity();

        return "Order created successfully. Product: "
                + product.getName()
                + ", Total Price: "
                + totalPrice;
    }
}