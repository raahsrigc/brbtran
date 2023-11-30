package com.erp.api.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelperBeans {
    static final int TIMEOUT = 5000;

//    public static ClientHttpRequestFactory getClientHttpRequestFactory() {
//        int timeout = 5000;
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
//                = new HttpComponentsClientHttpRequestFactory();
//        clientHttpRequestFactory.setConnectTimeout(timeout);
//        return clientHttpRequestFactory;
//    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
