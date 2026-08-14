package com.example.orderservice.service;

import com.example.orderservice.exception.ProductNotFoundException;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class OrderService {

    private final RestClient.Builder restClientBuilder;

    public OrderService(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    public String createOrder(Order order) {

        RestClient restClient = restClientBuilder.build();

        Product product;

        try {
            product = restClient.get()
                    .uri("http://localhost:8082/api/products/"
                            + order.getProductId())
                    .retrieve()
                    .body(Product.class);

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