package com.miage.altea.tp.battle_api.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PokemonType {
    private int id;
    private int baseExperience;
    private int level;
    private int height;
    private String name;
    private Sprites sprites;
    private Stats stats;
    private int weight;
    private List<String> types;
    private Integer hp;
    private Integer maxHp;

    private boolean ko = false;
    private boolean alive = true;
}