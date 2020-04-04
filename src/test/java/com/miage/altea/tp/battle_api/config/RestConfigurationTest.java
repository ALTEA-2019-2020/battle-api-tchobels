package com.miage.altea.tp.battle_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestConfigurationTest {

    @Test
    void restTemplate_shouldExist() {
        var restConfig = new RestConfiguration();
        restConfig.setUsername("User");
        restConfig.setPassword("Password");
        var restTemplate = restConfig.trainerApiRestTemplate();
        assertNotNull(restTemplate);
    }

    @Test
    void trainerApiRestTemplate_shouldHaveBasicAuth() {
        var restConfig = new RestConfiguration();
        restConfig.setPassword("user");
        restConfig.setUsername("password");
        var restTemplate = restConfig.trainerApiRestTemplate();

        assertNotNull(restTemplate);

        var interceptors = restTemplate.getInterceptors();
        assertNotNull(interceptors);
        assertEquals(1, interceptors.size());

        var interceptor = interceptors.get(0);
        assertNotNull(interceptor);

        assertEquals(BasicAuthenticationInterceptor.class, interceptor.getClass());
    }
}