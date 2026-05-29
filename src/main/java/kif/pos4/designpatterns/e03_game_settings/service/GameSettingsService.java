package kif.pos4.designpatterns.e03_game_settings.service;

import kif.pos4.designpatterns.e03_game_settings.dto.SettingsDto;
import kif.pos4.designpatterns.e03_game_settings.model.DifficultyType;
import kif.pos4.designpatterns.e03_game_settings.model.GameSettings;
import org.springframework.stereotype.Service;

@Service
public class GameSettingsService {

    private GameSettings settings;

    public GameSettingsService() {
        this.settings = GameSettings.getInstance();
    }

    /**     * Get current settings     */
    public SettingsDto getSettings() {
        return new SettingsDto(
                settings.getVolume(),
                settings.getLanguage(),
                settings.getDifficult()
        );
    }

    /**     * Update settings with validation     */
    public SettingsDto updateSettings(SettingsDto dto) {
        if (dto.getVolume() >= 0) {
            settings.setVolume(dto.getVolume());
        }

        if (dto.getLanguage() != null && !dto.getLanguage().isBlank()) {
            settings.setLanguage(dto.getLanguage());
        }

        if (dto.getDifficult() != null) {
            settings.setDifficulty(dto.getDifficult());
        }

        return getSettings();
    }
    /**     * Reset settings to default values     */
    public SettingsDto resetSettings() {
        settings.resetToDefaults();
        return getSettings();
    }
}
