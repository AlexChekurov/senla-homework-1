package com.alex.service;

import com.alex.annotations.Autowire;
import com.alex.annotations.Component;
import com.alex.repository.DatabaseInterface;


@Component
public class DatabaseService implements ServiceInterface {

    private DatabaseInterface database;

    @Autowire
    public void setDatabase(DatabaseInterface database) {
        this.database = database;
    }

    @Override
    public String execute() {
        return database.execute();
    }
}