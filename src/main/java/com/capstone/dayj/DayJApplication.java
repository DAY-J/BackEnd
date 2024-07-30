package com.capstone.dayj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DayJApplication {
    public static void main(String[] args) {
        SpringApplication.run(DayJApplication.class, args);
    }
    
}
