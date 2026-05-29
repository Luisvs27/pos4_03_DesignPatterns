package kif.pos4.designpatterns.e03_game_settings.dto;

import kif.pos4.designpatterns.e03_game_settings.model.DifficultyType;

public class SettingsDto {

    private Integer volume;
    private String language;
    private DifficultyType difficult;

    public SettingsDto(Integer volume, String language, DifficultyType difficult) {
        this.volume = volume;
        this.language = language;
        this.difficult = difficult;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public DifficultyType getDifficult() {
        return difficult;
    }

    public void setDifficult(DifficultyType difficult) {
        this.difficult = difficult;
    }
}
