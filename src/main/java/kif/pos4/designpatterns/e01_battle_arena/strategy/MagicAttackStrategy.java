package kif.pos4.designpatterns.e01_battle_arena.strategy;

public class MagicAttackStrategy implements IAttackStrategy{
    @Override
    public String attack(String characterName) {
        return characterName + "attacks with a magic!!";
    }
}
