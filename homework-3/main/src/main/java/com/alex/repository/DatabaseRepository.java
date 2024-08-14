package com.alex.repository;

import com.alex.annotations.Autowire;
import com.alex.annotations.Component;
import com.alex.utils.ParametersHolder;


@Component
public class DatabaseRepository implements DatabaseInterface {

    @Autowire
    private ParametersHolder parametersHolder;

    @Override
    public String execute() {
        return parametersHolder.getSomeText();
    }

}
