package com.example.orderservice.controller;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.UserResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserClient userClient;

    public OrderController(OrderService orderService,
                           UserClient userClient) {
        this.orderService = orderService;
        this.userClient = userClient;
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{orderId}/user")
    public UserResponse getOrderUser(@PathVariable Long orderId) {
        return userClient.getUser(1001L);
    }
}