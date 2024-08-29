package com.alex.homework4example.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParametersHolder {

    @Value("${my.param.db}")
    private String someText = "123";


    public String getSomeText() {
        return someText;
    }
}
