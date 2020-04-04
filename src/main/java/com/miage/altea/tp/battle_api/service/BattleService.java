package com.miage.altea.tp.battle_api.service;

import com.miage.altea.tp.battle_api.bo.Battle;
import com.miage.altea.tp.battle_api.bo.Pokemon;
import com.miage.altea.tp.battle_api.bo.Trainer;
import com.miage.altea.tp.battle_api.exception.ExceptionNotFound;
import com.miage.altea.tp.battle_api.pokemonTypes.PokemonTypeService;
import com.miage.altea.tp.battle_api.trainers.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


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

    public Battle createNewBattle(String trainer, String opponent) throws ExceptionNotFound {
        Trainer trainerA = trainerService.getTrainerByName(trainer);
        Trainer trainerB = trainerService.getTrainerByName(opponent);

        trainerA.getTeam().forEach(pokemon -> {
            pokemon.setType(pokemonTypeService.getPokemonType(pokemon.getPokemonTypeId()));
            pokemon.getType().getStats().setStatsBylevel(pokemon.getLevel());
        });

        trainerB.getTeam().forEach(pokemon -> {
            pokemon.setType(pokemonTypeService.getPokemonType(pokemon.getPokemonTypeId()));
            pokemon.getType().getStats().setStatsBylevel(pokemon.getLevel());
        });

        Battle battle = new Battle(UUID.randomUUID(), trainerA, trainerB);

        Pokemon pokemonChooseTrainerA = this.getPokemon(trainerA.getTeam());
        Pokemon pokemonChooseTrainerB = this.getPokemon(trainerB.getTeam());

        if (pokemonChooseTrainerA.getType().getStats().getSpeed() < pokemonChooseTrainerB.getType().getStats().getSpeed()) {
            battle.setTrainer(trainerB);
            battle.setOpponent(trainerA);
        }
        battle.getTrainer().setNextTurn(true);
        battle.getOpponent().setNextTurn(false);
        this.battlesList.put(battle.getUuid(), battle);
        return battle;
    }


    public Battle getBattle(UUID uuid) {
        return this.battlesList.get(uuid);
    }

    public Battle attackBattle(UUID uuid, String trainerName) throws ExceptionNotFound {

        Battle battle = this.battlesList.get(uuid);
        Trainer trainerA = battle.getTrainer().getName().equals(trainerName) ? battle.getTrainer() : battle.getOpponent();
        Trainer trainerB = battle.getTrainer().getName().equals(trainerName) ? battle.getOpponent() : battle.getTrainer();

        Pokemon pokemonChooseTrainerA = this.getPokemon(trainerA.getTeam());
        Pokemon pokemonChooseTrainerB = this.getPokemon(trainerB.getTeam());

        if (!trainerA.isNextTurn()) {
            throw new ExceptionNotFound();
        }

        int score = 2 + ((2 * pokemonChooseTrainerA.getLevel() / 5 +
                (2 * (pokemonChooseTrainerA.getType().getStats().getAttack() / pokemonChooseTrainerB.getType().getStats().getDefense()))));
        int defenseHp = pokemonChooseTrainerB.getType().getStats().getHp();
        int total = defenseHp - score;
        pokemonChooseTrainerB.getType().getStats().setHp(total < 0 ? 0 : total);
        pokemonChooseTrainerB.setAlive(total > 0);
        trainerA.setNextTurn(false);
        trainerB.setNextTurn(true);
        return battle;
    }


    private Pokemon getPokemon(List<Pokemon> pokemons) {
        return pokemons.stream().filter(Pokemon::isAlive).findFirst().get();
    }


}