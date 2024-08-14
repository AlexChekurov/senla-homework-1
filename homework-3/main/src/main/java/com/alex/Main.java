package com.alex;

import com.alex.controller.DatabaseController;
import com.alex.core.ApplicationContextInitializer;


public class Main {
    public static void main(String[] args) {
        var context = ApplicationContextInitializer.initializeContext("com.alex", Main.class);

        // Получение контроллера и вызов метода
        var controller = context.getBean(DatabaseController.class);
        controller.execute();

    }
}
