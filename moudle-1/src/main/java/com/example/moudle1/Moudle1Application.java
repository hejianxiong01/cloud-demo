package com.example.moudle1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
public class Moudle1Application {

    public static void main(final String[] args) {
        SpringApplication.run(Moudle1Application.class, args);
    }

}
