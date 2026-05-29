package kif.pos4.designpatterns.e03_game_settings.service;

import kif.pos4.designpatterns.e03_game_settings.dto.SettingsDto;
import kif.pos4.designpatterns.e03_game_settings.model.DifficultyType;
import kif.pos4.designpatterns.e03_game_settings.model.GameSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GameSettingsService Tests")
class GameSettingsServiceTest {

    private GameSettingsService service;

    @BeforeEach
    void setUp() {
        // Reset singleton to default state before each test
        GameSettings.getInstance().resetToDefaults();
        service = new GameSettingsService();
    }

    @Test
    @DisplayName("Default values should be correct")
    void testDefaultValues() {
        SettingsDto settings = service.getSettings();

        assertEquals(50, settings.getVolume(), "Default volume should be 50");
        assertEquals("EN", settings.getLanguage(), "Default language should be EN");
        assertEquals("NORMAL", settings.getDifficult(), "Default difficulty should be NORMAL");
    }

    @Test
    @DisplayName("Volume should be changeable")
    void testVolumeCanBeChanged() {
        SettingsDto newSettings = new SettingsDto(80, "EN", DifficultyType.NORMAL);
        SettingsDto result = service.updateSettings(newSettings);

        assertEquals(80, result.getVolume(), "Volume should be updated to 80");
    }

    @Test
    @DisplayName("Negative volume should be clamped to 0")
    void testNegativeVolumeClamping() {
        SettingsDto newSettings = new SettingsDto(-10, "EN", DifficultyType.NORMAL);
        SettingsDto result = service.updateSettings(newSettings);

        assertEquals(0, result.getVolume(), "Negative volume should be clamped to 0");
    }

    @Test
    @DisplayName("Volume over 100 should be clamped to 100")
    void testHighVolumeClamp() {
        SettingsDto newSettings = new SettingsDto(150, "EN", DifficultyType.NORMAL);
        SettingsDto result = service.updateSettings(newSettings);

        assertEquals(100, result.getVolume(), "Volume over 100 should be clamped to 100");
    }

    @Test
    @DisplayName("Language should be changeable")
    void testLanguageCanBeChanged() {
        SettingsDto newSettings = new SettingsDto(50, "DE", DifficultyType.NORMAL);
        SettingsDto result = service.updateSettings(newSettings);

        assertEquals("DE", result.getLanguage(), "Language should be updated to DE");
    }

    @Test
    @DisplayName("Difficulty should only accept valid values (EASY, NORMAL, HARD)")
    void testDifficultyValidation() {
        // Test valid value
        SettingsDto validSettings = new SettingsDto(50, "EN", DifficultyType.HARD);
        SettingsDto result = service.updateSettings(validSettings);
        assertEquals("HARD", result.getDifficult(), "Difficulty should be updated to HARD");

        // Test invalid value - should remain unchanged
        SettingsDto invalidSettings = new SettingsDto(50, "EN", DifficultyType.INVALID);
        result = service.updateSettings(invalidSettings);
        assertEquals("HARD", result.getDifficult(), "Invalid difficulty should be ignored");
    }

    @Test
    @DisplayName("Reset should restore default values")
    void testResetSettings() {
        // First, change some settings
        SettingsDto newSettings = new SettingsDto(90, "DE", DifficultyType.HARD);
        service.updateSettings(newSettings);

        // Verify they were changed
        SettingsDto changed = service.getSettings();
        assertEquals(90, changed.getVolume());
        assertEquals("DE", changed.getLanguage());
        assertEquals("HARD", changed.getDifficult());

        // Reset
        SettingsDto reset = service.resetSettings();

        // Verify defaults are restored
        assertEquals(50, reset.getVolume(), "Volume should be reset to 50");
        assertEquals("EN", reset.getLanguage(), "Language should be reset to EN");
        assertEquals("NORMAL", reset.getDifficult(), "Difficulty should be reset to NORMAL");
    }

    @Test
    @DisplayName("Valid difficulty levels EASY, NORMAL, HARD should all work")
    void testAllValidDifficultyLevels() {
        DifficultyType[] validDifficulties =
                {DifficultyType.EASY, DifficultyType.NORMAL, DifficultyType.HARD};

        for (DifficultyType difficulty : validDifficulties) {
            GameSettings.getInstance().resetToDefaults();
            service = new GameSettingsService();

            SettingsDto settings = new SettingsDto(50, "EN", difficulty);
            SettingsDto result = service.updateSettings(settings);

            assertEquals(difficulty, result.getDifficult(),
                    "Difficulty " + difficulty + " should be accepted");
        }
    }
}