package com.example.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@ConditionalOnClass(EntityManager.class)
public class CustomEndPointAutoConfig {

    @Autowired
    private EntityManager em;

    @Bean
    public CustomizeEndPoint myEndPoint() {
        return new CustomizeEndPoint(em);
    }

}
