package com.miage.altea.tp.battle_api.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainer {
    private String name;
    private List<Pokemon> team;
    private boolean nextTurn;
    private String password;

}
