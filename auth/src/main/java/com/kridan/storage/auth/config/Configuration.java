package com.kridan.storage.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
