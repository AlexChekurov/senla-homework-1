package com.alex.homework4example.simplecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alex.homework4example.simpleservice.Service;

@Component
public class DatabaseController {

    private final Service service;

    @Autowired
    public DatabaseController(Service service) {
        this.service = service;
    }

    public void execute() {
        String result = service.execute();
        System.out.println(result);
    }
}
