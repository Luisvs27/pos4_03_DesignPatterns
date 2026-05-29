package kif.pos4.designpatterns.e01_battle_arena.strategy;

import kif.pos4.designpatterns.e01_battle_arena.model.GameCharacter;

public class SpecialAttackStrategy implements IAttackStrategy {

    @Override
    public String attack(String characterName) {
        return characterName + " uses a special attack!!";
    }
}
