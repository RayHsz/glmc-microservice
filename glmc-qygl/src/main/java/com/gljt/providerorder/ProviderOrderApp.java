package com.gljt.providerorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
* @Author liwei
* @Date 2022/8/19 14:05
* @Version 1.0
*/
@SpringBootApplication
@EnableEurekaClient
public class ProviderOrderApp {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOrderApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
