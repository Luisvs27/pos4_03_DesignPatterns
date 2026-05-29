package kif.pos4.designpatterns.e03_game_settings.model;

import lombok.Data;
import lombok.Setter;

public class GameSettings {

    private static final GameSettings INSTANCE = new GameSettings();

    // Default values
    private static final int DEFAULT_VOLUME = 50;
    private static final String DEFAULT_LANGUAGE = "EN";
    private static final DifficultyType DEFAULT_DIFFICULTY = DifficultyType.NORMAL;


    @Setter
    private Integer volume;
    @Setter
    private String language;
    @Setter
    private DifficultyType difficulty;

    private GameSettings() {}

    public static GameSettings getInstance() {
        return INSTANCE;
    }

    public Integer getVolume() {
        return volume != null ? volume : DEFAULT_VOLUME;
    }

    public String getLanguage() {

        if(language != null) {
            return language;
        }
        return "Language is not defined";
    }

    public DifficultyType getDifficult() {
        if(difficulty != null) {
            return difficulty;
        }
        return null;
    }


    // Reset to default values
    public void resetToDefaults() {
        this.volume = DEFAULT_VOLUME;
        this.language = DEFAULT_LANGUAGE;
        this.difficulty = DEFAULT_DIFFICULTY;
    }
}
