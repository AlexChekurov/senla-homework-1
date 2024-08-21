package com.example.utils;

import com.example.annotations.Component;
import com.example.annotations.Value;


@Component
public class ParametersHolder {

    @Value("my.param.db")
    private String someText = "123";

    public String getSomeText() {
        return someText;
    }
}
