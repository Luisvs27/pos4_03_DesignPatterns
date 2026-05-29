package kif.pos4.designpatterns.e01_battle_arena.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import kif.pos4.designpatterns.e01_battle_arena.model.GameCharacter;
import kif.pos4.designpatterns.e01_battle_arena.repository.GameCharacterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

@Slf4j
@Service
public class DbInitService {

    private final GameCharacterRepository gameCharacterRepository;

    List<GameCharacter> listCharacter;

    public DbInitService(GameCharacterRepository gameCharacterRepository) {
        this.gameCharacterRepository = gameCharacterRepository;
    }

    @PostConstruct
    public void initDb() {

        ObjectMapper mapper = new ObjectMapper();

        try {
            listCharacter = mapper.readValue(
                    getClass().getClassLoader().getResourceAsStream("chars.json"),
                    new TypeReference<>() {});

            System.out.println("DB INIT 1");
            gameCharacterRepository.saveAll(listCharacter);

            log.info("Data stored in DB!!");

        }
        catch (Exception e) {
            log.error("Error while reading json file", e);
            log.error(e.getMessage());

            throw new RuntimeException(e);
        }
    }



}
