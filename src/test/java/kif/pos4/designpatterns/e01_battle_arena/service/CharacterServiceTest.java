package kif.pos4.designpatterns.e01_battle_arena.service;

import kif.pos4.designpatterns.e01_battle_arena.dto.AttackResponseDto;
import kif.pos4.designpatterns.e01_battle_arena.model.AttackTypes;
import kif.pos4.designpatterns.e01_battle_arena.model.GameCharacter;
import kif.pos4.designpatterns.e01_battle_arena.repository.GameCharacterRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CharacterServiceTest {

    @Test
    void attackShouldReturnAttackText() {
        // wir mocken den repository, da wir nicht auf eine DB zugreifen wollen
        GameCharacterRepository repository = mock(GameCharacterRepository.class);

        // wir mocken ein GameCharacter,da wir keine DB vorhanden haben
        // und wir nicht auf ein spezifisches Character zugreifen wollen
        GameCharacter mockCharacter = new GameCharacter(
                "Robin hood",
                "Heroes",
                1000,
                AttackTypes.SWORD.typesStrategy()
        );
        // wenn wir "findById(1)" aufrufen, dann wollen wir unseren Mock-Character
        mockCharacter.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(mockCharacter));

        // Unser Service zum Testen braucht den Repository
        CharacterService service = new CharacterService(repository);

        //  Dann rufen wir attack(1) auf, um das result zu bekommen
        Optional<AttackResponseDto> result = service.attackCharacter(1);

        //  Assert, dass das result nicht leer ist
        assertTrue(result.isPresent());

        assertEquals("Robin hood attacks with a sword!!", result.get().getResult());

    }


}