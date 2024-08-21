package com.example;

import com.example.controller.DatabaseController;
import com.example.core.ApplicationContextInitializer;


public class Main {
    public static void main(String[] args) {
        var context = ApplicationContextInitializer.initializeContext(Main.class.getPackage().getName(), Main.class);

        // Get the controller and call the method
        var controller = context.getBean(DatabaseController.class);
        controller.execute();

    }
}
