package kif.pos4.designpatterns.e01_battle_arena.repository;

import kif.pos4.designpatterns.e01_battle_arena.model.GameCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameCharacterRepository
        extends JpaRepository<GameCharacter, Integer> {

    List<GameCharacter> findByTeamIgnoreCase(String team);



}
