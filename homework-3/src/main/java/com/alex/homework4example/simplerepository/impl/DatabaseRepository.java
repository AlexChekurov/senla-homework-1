package com.alex.homework4example.simplerepository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alex.homework4example.simplerepository.Database;
import com.alex.homework4example.utils.ParametersHolder;

@Component
public class DatabaseRepository implements Database {

    @Autowired
    private ParametersHolder parametersHolder;

    @Override
    public String execute() {
        return parametersHolder.getSomeText();
    }
}
