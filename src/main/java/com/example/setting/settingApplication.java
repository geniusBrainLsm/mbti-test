package com.example.setting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class settingApplication {

    public static void main(String[] args) {
        SpringApplication.run(settingApplication.class, args);
    }

}
