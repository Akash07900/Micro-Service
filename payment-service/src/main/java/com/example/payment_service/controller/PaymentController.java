package com.example.payment_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping("/api/payments/test")
    public String testPayment() {
        return "Payment Service is working";
    }
}