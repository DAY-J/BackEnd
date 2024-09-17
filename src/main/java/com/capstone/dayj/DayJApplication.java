package com.capstone.dayj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling // 스케줄러 활성화
@ConfigurationPropertiesScan
@SpringBootApplication
public class DayJApplication {
    public static void main(String[] args) {
        SpringApplication.run(DayJApplication.class, args);
    }
    
}
