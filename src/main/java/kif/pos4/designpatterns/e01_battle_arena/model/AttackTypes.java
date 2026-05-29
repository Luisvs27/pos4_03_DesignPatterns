package kif.pos4.designpatterns.e01_battle_arena.model;

import kif.pos4.designpatterns.e01_battle_arena.strategy.IAttackStrategy;

public enum AttackTypes {
    SPECIAL,
    BOW,
    SWORD,
    MAGIC;

    public AttackTypes typesStrategy() {
        return this;
    }
    AttackTypes() {}


}



