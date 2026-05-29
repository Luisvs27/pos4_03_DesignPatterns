package kif.pos4.designpatterns.e01_battle_arena.strategy;

import kif.pos4.designpatterns.e01_battle_arena.model.GameCharacter;

public class AttackContext {

    private IAttackStrategy strategy;


    public void setStrategy(IAttackStrategy strategy) {
        this.strategy = strategy;
    }

    public String executeAttack(String characterName){
        if(strategy == null) {
            return characterName + "has not attack strategy assigned!!";
        } else {
            return
            strategy.attack(characterName);
        }
    }

}
