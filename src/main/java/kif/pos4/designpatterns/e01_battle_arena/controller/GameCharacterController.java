package kif.pos4.designpatterns.e01_battle_arena.controller;

import kif.pos4.designpatterns.e01_battle_arena.dto.AttackResponseDto;
import kif.pos4.designpatterns.e01_battle_arena.dto.AttackTypeDto;
import kif.pos4.designpatterns.e01_battle_arena.model.GameCharacter;
import kif.pos4.designpatterns.e01_battle_arena.repository.GameCharacterRepository;
import kif.pos4.designpatterns.e01_battle_arena.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/game")
public class GameCharacterController {

    private final GameCharacterRepository gameCharacterRepository;
    private final CharacterService characterService;
    public GameCharacterController(GameCharacterRepository gameCharacterRepository,
                                   CharacterService characterService) {
        this.gameCharacterRepository = gameCharacterRepository;
        this.characterService = characterService;
    }

    @GetMapping("/characters")
    public List<GameCharacter> getAllCharacters(
            @RequestParam(value = "team", required = false) String team
    ) {
        return characterService.findAllAndAlsoByTeam(team).getBody();
    }

    @PostMapping("/characters")
    public ResponseEntity<GameCharacter> addNewCharacter(
            @RequestBody GameCharacter gameCharacter
    ) {
        GameCharacter savedCharacter =
                characterService.createCharacter(gameCharacter);

        return ResponseEntity.status(201).body(savedCharacter);
    }
    @PutMapping("/characters/{id}/attack-type")
    public ResponseEntity<GameCharacter> updateAttackType(
            @PathVariable Integer id,
            @RequestBody AttackTypeDto characterAttack
    ) {
        Optional<GameCharacter> updateCharacter =
                characterService.updateAttackType(id, characterAttack.getAttackType());

        return updateCharacter.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/characters/{id}/attack")
    public ResponseEntity<AttackResponseDto> attackCharacter(
            @PathVariable Integer id
    ) {
        Optional<AttackResponseDto> attackResult =
                characterService.attackCharacter(id);

        /**
         * if (attackResult.isEmpty())
         *             return ResponseEntity.notFound().build();
         *         else {
         *             return
         *             ResponseEntity.status(200).body(attackResult.get());
         *         }*/

        // Better way :
        return attackResult.map(
                        attackResponseDto ->
                                ResponseEntity.status(200)
                                        .body(attackResponseDto))
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }


}
