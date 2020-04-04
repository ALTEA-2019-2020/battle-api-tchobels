package com.miage.altea.tp.battle_api.trainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TrainerServiceIntegrationTest {
    @Autowired
    TrainerService service;

    @Qualifier("trainerApiRestTemplate")
    @Autowired
    RestTemplate restTemplate;

    @Test
    void serviceAndTemplateShouldNotBeNull() {
        assertNotNull(service);
        assertNotNull(restTemplate);
    }
}
