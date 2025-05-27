package com.proje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.proje", "com.proje.main", "com.proje.controller"})
public class Uygulama {
    public static void main(String[] args) {
        System.out.println("Spring Boot başlatılıyor...");
        SpringApplication.run(Uygulama.class, args);
    }
}