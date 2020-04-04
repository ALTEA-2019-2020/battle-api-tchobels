package com.miage.altea.tp.battle_api.pokemonTypes;
import com.miage.altea.tp.battle_api.bo.PokemonType;

import java.util.List;

public interface PokemonTypeService {
    List<PokemonType> listPokemonsTypes();
    PokemonType getPokemonType(int id);
}
