package kif.pos4.designpatterns.e01_battle_arena.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GameCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String team;

    private Integer powerLevel;

    private AttackTypes attackType;

    /**
     * Erstellt ein neues GameCharacter Objekt,dass vermutlich noch nicht
     * in der Dantenbank ist (daher keine ID in der Parameterliste).
     * @param name
     * @param team
     * @param powerLevel
     * @param attackType
     * */
    public GameCharacter(String name,
                         String team,
                         Integer powerLevel,
                         AttackTypes attackType) {
        this.name = name;
        this.team = team;
        this.powerLevel = powerLevel;
        this.attackType = attackType;
    }


}
