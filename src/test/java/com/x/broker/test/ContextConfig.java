package com.x.broker.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfig {

    @Bean
    public String text() {
        return new String("Hello World");
    }
}
