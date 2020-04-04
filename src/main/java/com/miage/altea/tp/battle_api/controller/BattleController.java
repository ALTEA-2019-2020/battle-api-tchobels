package com.miage.altea.tp.battle_api.controller;

import com.miage.altea.tp.battle_api.bo.Battle;
import com.miage.altea.tp.battle_api.exception.ExceptionNotFound;
import com.miage.altea.tp.battle_api.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/battles")
public class BattleController {

    @Autowired
    BattleService battleService;

    @PostMapping
    public Battle setNewBattle(@RequestParam String trainer, @RequestParam String opponent) throws ExceptionNotFound {
        return battleService.createNewBattle(trainer, opponent);
    }

    @GetMapping
    public List<Battle> getBattles() {
        return new ArrayList<>(battleService.getBattles().values());
    }

    @GetMapping("/{uuid}")
    public Battle getBattle(@PathVariable UUID uuid) {
        return battleService.getBattle(uuid);
    }

    @PostMapping("/{uuid}/{trainerName}/attack")
    public Battle battleAttack(@PathVariable UUID uuid, @PathVariable String trainerName) throws ExceptionNotFound {
        return battleService.attackBattle(uuid, trainerName);
    }
}
