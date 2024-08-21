package com.example.controller;

import com.example.annotations.Autowire;
import com.example.annotations.Component;
import com.example.service.Service;


@Component
public class DatabaseController {

    private final Service service;

    @Autowire
    public DatabaseController(Service service) {
        this.service = service;
    }

    public void execute() {
        String result = service.execute();
        System.out.println(result);
    }

}
