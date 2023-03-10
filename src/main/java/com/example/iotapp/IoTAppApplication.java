package com.example.iotapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class IoTAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoTAppApplication.class, args);
    }

}
