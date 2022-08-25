package com.gljt.provideritem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderItemApp {

    public static void main(String[] args) {
        SpringApplication.run(ProviderItemApp.class, args);
    }

}
