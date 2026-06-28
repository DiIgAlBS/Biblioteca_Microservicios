package com.Fullstack1.Microservicio3_Prestamos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient // Conecta este microservicio a Eureka
@EnableFeignClients    // Permitirá que este microservicio use Feign para hablar con otros
public class Microservicio3PrestamosApplication {

    public static void main(String[] args) {
        SpringApplication.run(Microservicio3PrestamosApplication.class, args);
    }
}
