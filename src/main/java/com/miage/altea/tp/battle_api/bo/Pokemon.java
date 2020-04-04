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
    private int level;
    private int pokemonTypeId;
    private PokemonType type;
    private boolean alive = true;
}
