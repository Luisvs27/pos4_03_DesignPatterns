package kif.pos4.designpatterns.e01_battle_arena.service;

import kif.pos4.designpatterns.e01_battle_arena.dto.AttackResponseDto;
import kif.pos4.designpatterns.e01_battle_arena.model.AttackTypes;
import kif.pos4.designpatterns.e01_battle_arena.model.GameCharacter;
import kif.pos4.designpatterns.e01_battle_arena.repository.GameCharacterRepository;
import kif.pos4.designpatterns.e01_battle_arena.strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final GameCharacterRepository gameCharacterRepository;

    public String attack(GameCharacter character) {

        AttackContext characterContext = new AttackContext();

        if ("SWORD".equalsIgnoreCase(String.valueOf(character.getAttackType()))) {
            characterContext.setStrategy(new SwordAttackStrategy());
        } else if ("BOW".equalsIgnoreCase(String.valueOf(character.getAttackType()))){
            characterContext.setStrategy(new BowAttackStrategy());
        }else if("SPECIAL".equalsIgnoreCase(String.valueOf(character.getAttackType()))){
            characterContext.setStrategy(new SpecialAttackStrategy());
        }else if("MAGIC".equalsIgnoreCase(String.valueOf(character.getAttackType()))){
            characterContext.setStrategy(new MagicAttackStrategy());
        }
        // .getAttackType() to set this strategy to the context
        return characterContext.executeAttack(character.getName());
    }
    public ResponseEntity<List<GameCharacter>> findAllAndAlsoByTeam(String team){
        if (team == null) {
            System.out.format("GET RESPONSE SUCCESS: All characters are returned");
            return ResponseEntity.status(200)
                    .body(gameCharacterRepository.findAll());
        } else {
            System.out.format("GET RESPONSE SUCCESS: " +
                    "All characters of team %s are returned", team);
            return ResponseEntity.status(200)
                    .body(gameCharacterRepository.findByTeamIgnoreCase(team));
        }
    }

    // POST
    public GameCharacter createCharacter(GameCharacter gameCharacter){
        return gameCharacterRepository.save(gameCharacter);
    }

    // PUT
    public Optional<GameCharacter> updateAttackType (Integer id, String attackType) {
        Optional<GameCharacter> character = gameCharacterRepository.findById(id);

        if(character.isPresent()) {
            GameCharacter characterToUpdate = character.get();
            characterToUpdate.setAttackType(AttackTypes.valueOf(attackType));
            System.out.format("RESOURCE UPDATED SUCCESSFULLY: Character %s updated",
                    characterToUpdate.getName());

            return Optional.of(gameCharacterRepository.save(characterToUpdate));
        } else {
            return Optional.empty();
        }
    }

    // GET Response from a Character
    public Optional<AttackResponseDto> attackCharacter (Integer id){
        Optional<GameCharacter> optionalCharacter =
                gameCharacterRepository.findById(id);

        if(optionalCharacter.isEmpty())
            return Optional.empty();
        else {
            GameCharacter gameCharacter = optionalCharacter.get();

            // 2. AttackContext erstellen
            AttackContext attackContext = new AttackContext();

            // 3. AttackContext wählt die passende Strategie aus
            attackContext.executeAttack(gameCharacter.getName());

            // 4. Strategy is running and character does something
            String result = attack(gameCharacter);

            // 5. Ergebnis zurück
            AttackResponseDto attackResult = new AttackResponseDto(
                    gameCharacter.getName(),
                    gameCharacter.getAttackType().name(),
                    result
            );
            // 6. Now we know who and with what attack did
            // the character in the console:
            System.out.println(result);

            return Optional.of(attackResult);
        }
    }

}
