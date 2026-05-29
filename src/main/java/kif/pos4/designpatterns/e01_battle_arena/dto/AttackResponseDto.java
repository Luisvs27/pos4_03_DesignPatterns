package kif.pos4.designpatterns.e01_battle_arena.dto;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class AttackResponseDto {
    
    private String characterName;
    private String attackType;
    private String result;


    @Override
    public String toString() {
        return "AttackResponseDto{" +
                "characterName='" + characterName + '\'' +
                ", attackType='" + attackType + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
