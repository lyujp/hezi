package com.lyujp.heziservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lyujp.heziservice.mapper")
public class HeziServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeziServiceApplication.class, args);
    }

}
