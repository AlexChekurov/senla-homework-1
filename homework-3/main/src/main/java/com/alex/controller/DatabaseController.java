package com.alex.controller;

import com.alex.annotations.Autowire;
import com.alex.annotations.Component;
import com.alex.service.ServiceInterface;


@Component
public class DatabaseController {

    private final ServiceInterface service;

    @Autowire
    public DatabaseController(ServiceInterface service) {
        this.service = service;
    }

    public void execute() {
        String result = service.execute();
        System.out.println(result);
    }

}
