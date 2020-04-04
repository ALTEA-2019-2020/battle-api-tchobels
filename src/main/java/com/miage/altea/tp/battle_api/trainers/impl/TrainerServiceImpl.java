package com.miage.altea.tp.battle_api.trainers.impl;

import com.miage.altea.tp.battle_api.bo.Trainer;
import com.miage.altea.tp.battle_api.trainers.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrainerServiceImpl implements TrainerService {

    private RestTemplate restTemplate;

    private String trainerServiceUrl;

    @Autowired
    @Qualifier("trainerApiRestTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Trainer getTrainerByName(String name) {
        return this.restTemplate.getForObject(trainerServiceUrl + "/trainers/" + name, Trainer.class);
    }

    @Override
    public Trainer[] getAllTrainers() {
        return this.restTemplate.getForObject(trainerServiceUrl + "/trainers/", Trainer[].class);
    }

    @Value("${trainer.service.url}")
    public void setTrainerApiUrl(String url) {
        this.trainerServiceUrl = url;
    }
}
