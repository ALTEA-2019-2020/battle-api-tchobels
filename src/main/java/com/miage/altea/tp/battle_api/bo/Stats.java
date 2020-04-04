package com.miage.altea.tp.battle_api.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stats {
    private Integer speed;
    private Integer defense;
    private Integer attack;
    private Integer hp;
    private Integer maxHp;

    public void setStatsBylevel(int level) {
        this.speed = getStatByLevel(speed,level);
        this.defense = getStatByLevel(defense,level);
        this.attack = getStatByLevel(attack,level);
        this.hp = getHpByLevel(hp,level);
        this.maxHp = hp;
    }

    private int getStatByLevel(int stat, int level) {
        return (int) (5.0 + (double) stat * ((double) level / 50.0));
    }

    private int getHpByLevel(int stat, int level) {
        return (int) (10.0 + (double) level + (double) stat * ((double) level / 50.0));
    }
}
