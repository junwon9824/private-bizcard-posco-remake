package com.ssafy.businesscard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching

public class BusinesscardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinesscardApplication.class, args);
        
    }

}
