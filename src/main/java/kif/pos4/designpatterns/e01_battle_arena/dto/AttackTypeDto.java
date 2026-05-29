package kif.pos4.designpatterns.e01_battle_arena.dto;

@lombok.Getter
@lombok.Setter
public class AttackTypeDto {

    private String name;
    private String team;
    private Integer powerLevel;
    private String attackType;

    public AttackTypeDto(String name,
                         String team,
                         Integer powerLevel,
                         String attackType) {
        this.name = name;
        this.team = team;
        this.powerLevel = powerLevel;
        this.attackType = attackType;
    }
    public AttackTypeDto() {}

    @Override
    public String toString() {
        return "AttackTypeDto{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", powerLevel=" + powerLevel +
                ", attackType='" + attackType + '\'' +
                '}';
    }
}
