package com.example.service.impl;

import com.example.annotations.Autowire;
import com.example.annotations.Component;
import com.example.repository.Database;
import com.example.service.Service;


@Component
public class DatabaseService implements Service {

    private Database database;

    @Autowire
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String execute() {
        return database.execute();
    }
}