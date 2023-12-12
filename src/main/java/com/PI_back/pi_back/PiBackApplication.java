package com.PI_back.pi_back;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PiBackApplication {
    public static final Logger Logger = LoggerFactory.getLogger(PiBackApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(PiBackApplication.class, args);
        Logger.info("**********************");
        Logger.info("Server is running...");
        Logger.info("**********************");

    }

}
