package com.miage.altea.tp.battle_api.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {
    private PokemonType type;
    private int level;
    private int pokemonTypeId;
    private Integer hp;
    private Integer maxHp;

    private boolean ko = false;
    private boolean alive = true;
}
