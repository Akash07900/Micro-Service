package com.example.user_service.controller;

import com.example.user_service.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {

        return new UserResponse(
                id,
                "Akash",
                "Akash@example.com"
        );
    }
}