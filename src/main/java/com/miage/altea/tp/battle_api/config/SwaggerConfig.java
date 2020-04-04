package com.miage.altea.tp.battle_api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket jsonApi() {
        return new Docket(SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.miage.altea.tp.battle_api"))
                .paths(PathSelectors.regex("(?!/error).+"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "REST API",
                "Rest API for pokemon type",
                "API V1",
                "Terms of service",
                new Contact("GameFRIC company", "http://Gfreak.fr/", "alexis.bels@outlook.fr"),
                "License of API", "API license URL", Collections.emptyList());
    }
}