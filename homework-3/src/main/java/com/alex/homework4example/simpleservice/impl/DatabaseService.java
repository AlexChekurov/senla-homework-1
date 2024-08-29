package com.alex.homework4example.simpleservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alex.homework4example.simplerepository.Database;
import com.alex.homework4example.simpleservice.Service;

@Component
public class DatabaseService implements Service {

    private Database database;

    @Autowired
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String execute() {
        return database.execute();
    }
}
