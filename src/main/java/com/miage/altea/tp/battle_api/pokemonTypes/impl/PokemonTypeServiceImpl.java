package com.miage.altea.tp.battle_api.pokemonTypes.impl;

import com.miage.altea.tp.battle_api.bo.PokemonType;
import com.miage.altea.tp.battle_api.pokemonTypes.PokemonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {

    @Autowired
    @Qualifier("pokemonTypeApiRestTemplate")
    RestTemplate pokemonTypeApiRestTemplate;

    String pokemonServiceUrl = "http://localhost:8080";


    public List<PokemonType> listPokemonsTypes() {
        PokemonType[] pokemonTypes = pokemonTypeApiRestTemplate.getForObject(pokemonServiceUrl + "/pokemon-types/", PokemonType[].class);
        return pokemonTypes != null ? Arrays.asList(pokemonTypes) : new ArrayList<>();
    }

    @Override
    public PokemonType getPokemonType(int id) {
        return pokemonTypeApiRestTemplate.getForObject(pokemonServiceUrl + "/pokemon-types/{id}", PokemonType.class, id);
    }


    @Autowired
    public void setRestTemplate(@Qualifier("pokemonTypeApiRestTemplate") RestTemplate pokemonTypeApiRestTemplate) {
        this.pokemonTypeApiRestTemplate = pokemonTypeApiRestTemplate;
    }

    @Value("${pokemonType.service.url}")
    public void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.pokemonServiceUrl = pokemonServiceUrl;
    }
}
