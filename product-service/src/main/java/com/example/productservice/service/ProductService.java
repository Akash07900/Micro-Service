package com.example.productservice.service;

import com.example.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public ProductService() {

        products.add(new Product(1L, "Laptop", 60000));
        products.add(new Product(2L, "Mobile", 25000));
        products.add(new Product(3L, "Headphones", 3000));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Long id) {

        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}