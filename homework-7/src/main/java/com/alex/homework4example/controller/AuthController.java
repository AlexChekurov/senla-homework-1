package com.alex.homework4example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/login")
    public void login() {
        // Этот метод может быть пустым. Аутентификация обрабатывается фильтром.
    }

    @GetMapping("/test")
    public String test() {
        return "AuthController is working";
    }
}
