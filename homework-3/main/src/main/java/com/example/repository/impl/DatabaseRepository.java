package com.example.repository.impl;

import com.example.annotations.Autowire;
import com.example.annotations.Component;
import com.example.repository.Database;
import com.example.utils.ParametersHolder;


@Component
public class DatabaseRepository implements Database {

    @Autowire
    private ParametersHolder parametersHolder;

    @Override
    public String execute() {
        return parametersHolder.getSomeText();
    }

}
