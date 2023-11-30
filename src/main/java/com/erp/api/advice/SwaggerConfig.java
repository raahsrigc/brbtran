package com.erp.api.advice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Component
public class SwaggerConfig {


	@Configuration
	public class OpenAPIConfiguration {

	    @Bean
		@Profile("dev")
	    public OpenAPI openAPI1() {
	        return new OpenAPI()
	                .addServersItem(new Server().url("http://localhost:8091/"))
            .addServersItem(new Server().url("http://localhost:8091/"));

	    }
	    
//	    @Bean
//		@Profile("dev")
//	    public OpenAPI openAPI1() {
//	        return new OpenAPI()
//	                .addServersItem(new Server().url("https://garance.tangerine.insure/b2btran"))
//            .addServersItem(new Server().url("https://garance.tangerine.insure/b2btran"));
//
//	    }

		@Bean
		@Profile("preprod")
	    public OpenAPI openAPI2() {
	        return new OpenAPI()
	                .addServersItem(new Server().url("https://valaria.globalcognito.com/b2btran/"))
            .addServersItem(new Server().url("https://valaria.globalcognito.com/b2btran/"));

	    }


		@Bean
		@Profile("uat")
	    public OpenAPI openAPI3() {
	        return new OpenAPI()
	                .addServersItem(new Server().url("http://3.8.240.105:8090/b2btran/"))
            .addServersItem(new Server().url("http://3.8.240.105:8090/b2btran/"));

	    }
		
		@Bean
		@Profile("prod")
	    public OpenAPI openAPI4() {
	        return new OpenAPI()
	                .addServersItem(new Server().url("http://localhost:8091/b2btran/"))
            .addServersItem(new Server().url("http://localhost:8091/b2btran/"));

	    }
		
		@Bean
		@Profile("casavalive")
	    public OpenAPI openAPI5() {
	        return new OpenAPI()
	                .addServersItem(new Server().url("https://middleware-bloom.casava.com/b2btran/"))
            .addServersItem(new Server().url("https://middleware-bloom.casava.com/b2btran/"));

	    }
	}

}

