package com.example.kojimall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KojiMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(KojiMallApplication.class, args);
    }

}
