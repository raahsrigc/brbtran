package com.erp.api.advice;

import lombok.Getter;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class RestCallApi {

    private String uploadImage;
    private String kycDetails;
}
