package com.caravelo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.caravelo")
public class CaraveloApp {
    public static void main(String[] args) {
        SpringApplication.run(CaraveloApp.class, args);
    }
}
