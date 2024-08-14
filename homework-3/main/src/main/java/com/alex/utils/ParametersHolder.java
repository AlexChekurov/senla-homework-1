package com.alex.utils;

import com.alex.annotations.Component;
import com.alex.annotations.Value;


@Component
public class ParametersHolder {

    @Value("my.param.db")
    private String someText = "123";

    public String getSomeText() {
        return someText;
    }
}
