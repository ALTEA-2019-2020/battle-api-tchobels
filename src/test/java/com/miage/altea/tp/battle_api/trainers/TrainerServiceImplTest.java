package com.miage.altea.tp.battle_api.trainers;

import com.miage.altea.tp.battle_api.bo.Trainer;
import com.miage.altea.tp.battle_api.trainers.impl.TrainerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TrainerServiceImplTest {
    @Test
    void getTrainer_shouldCallTheRemoteService() {
        var url = "http://localhost:8085";

        var restTemplate = mock(RestTemplate.class);
        var trainerServiceImpl = new TrainerServiceImpl();
        trainerServiceImpl.setRestTemplate(restTemplate);
        trainerServiceImpl.setTrainerApiUrl(url);

        var trainer = new Trainer();
        trainer.setName("Ash");

        var expectedUrl = "http://localhost:8080/trainers/" + trainer.getName();
        when(restTemplate.getForObject(expectedUrl, Trainer.class)).thenReturn(trainer);

        var resultTrainer = trainerServiceImpl.getTrainerByName(trainer.getName());

        assertNotNull(resultTrainer);
        verify(restTemplate).getForObject(expectedUrl, Trainer.class);
    }
}