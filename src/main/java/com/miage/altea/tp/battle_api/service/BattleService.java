package com.miage.altea.tp.battle_api.service;

import com.miage.altea.tp.battle_api.bo.Battle;
import com.miage.altea.tp.battle_api.bo.Pokemon;
import com.miage.altea.tp.battle_api.bo.PokemonType;
import com.miage.altea.tp.battle_api.bo.Trainer;
import com.miage.altea.tp.battle_api.exception.ExceptionNotFound;
import com.miage.altea.tp.battle_api.pokemonTypes.PokemonTypeService;
import com.miage.altea.tp.battle_api.trainers.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BattleService {

    private HashMap<UUID, Battle> battlesList = new HashMap<>();

    @Autowired
    TrainerService trainerService;

    @Autowired
    PokemonTypeService pokemonTypeService;

    public HashMap<UUID, Battle> getBattles() {
        return battlesList;
    }

    public UUID getUuid(String trainer, String opponent) throws ExceptionNotFound {
        Trainer trainerA = trainerService.getTrainerByName(trainer);
        Trainer trainerB = trainerService.getTrainerByName(opponent);

        List<PokemonType> pokemonsTrainerA = trainerA.getTeam().stream()
                .map((Pokemon p) -> pokemonTypeService.getPokemonType(p.getPokemonTypeId()))
                .collect(Collectors.toList());

        List<PokemonType> pokemonsTrainerB = trainerB.getTeam().stream()
                .map((Pokemon p) -> pokemonTypeService.getPokemonType(p.getPokemonTypeId()))
                .collect(Collectors.toList());

        trainerA.setPokemonTypes(pokemonsTrainerA);
        trainerB.setPokemonTypes(pokemonsTrainerB);

        Battle battle = new Battle(UUID.randomUUID(),trainerA,trainerB);

        PokemonType pokemonChooseTrainerA = this.getPokemon(trainerA.getPokemonTypes());
        PokemonType pokemonChooseTrainerB = this.getPokemon(trainerB.getPokemonTypes());

        if (pokemonChooseTrainerA.getStats().getSpeed() < pokemonChooseTrainerB.getStats().getSpeed()) {
            battle.setTrainer(trainerB);
            battle.setOpponent(trainerA);
        }
        battle.getTrainer().setNextTurn(true);
        battle.getOpponent().setNextTurn(false);
        this.battlesList.put(battle.getUuid(), battle);
        return battle.getUuid();

    }


    public Battle getBattle(UUID uuid) {
        return this.battlesList.get(uuid);
    }

    public Battle attackBattle(UUID uuid, String trainerName) throws ExceptionNotFound {

        Battle battle = this.battlesList.get(uuid);
        Trainer trainerA = battle.getTrainer().getName().equals(trainerName) ? battle.getTrainer() : battle.getOpponent();
        Trainer trainerB = battle.getTrainer().getName().equals(trainerName) ? battle.getOpponent() : battle.getTrainer();

        PokemonType pokemonChooseTrainerA = this.getPokemon(trainerA.getPokemonTypes());
        PokemonType pokemonChooseTrainerB = this.getPokemon(trainerB.getPokemonTypes());

        if (!trainerA.isNextTurn()) {
            throw new ExceptionNotFound();
        }

        int score = 2 + ((2 * pokemonChooseTrainerA.getLevel() / 5 +
                (2 * (pokemonChooseTrainerA.getStats().getAttack() / pokemonChooseTrainerB.getStats().getDefense()))));
        int defenseHp = pokemonChooseTrainerB.getStats().getHp();
        int total = defenseHp - score;
        pokemonChooseTrainerB.getStats().setHp(Math.max(total, 0));
        pokemonChooseTrainerB.setAlive(total > 0);
        trainerA.setNextTurn(false);
        trainerB.setNextTurn(true);
        return battle;

    }


    private PokemonType getPokemon(List<PokemonType> pokemons) {
        return pokemons.stream().filter(PokemonType::isAlive).findFirst().get();
    }


}