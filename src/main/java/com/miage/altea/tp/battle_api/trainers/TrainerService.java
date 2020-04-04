package com.miage.altea.tp.battle_api.trainers;


import com.miage.altea.tp.battle_api.bo.Trainer;

public interface TrainerService {
    Trainer[] getAllTrainers();
    Trainer getTrainerByName(String name);
}