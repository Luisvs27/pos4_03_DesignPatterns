package kif.pos4.designpatterns.e03_game_settings.controller;

import kif.pos4.designpatterns.e03_game_settings.dto.SettingsDto;
import kif.pos4.designpatterns.e03_game_settings.service.GameSettingsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
@AllArgsConstructor
public class GameSettingsController {

    private final GameSettingsService settingsService;

    // Endpoint to get current settings
        @GetMapping
        public SettingsDto getCurrentSettings() {
            return settingsService.getSettings();
        }

        // Endpoint to update settings
        @PutMapping
        public SettingsDto updateSettings(
                @RequestBody SettingsDto settingsDto
                ) {

            return settingsService.updateSettings(settingsDto);
        }

        // Endpoint to reset settings to default values
        @RequestMapping("/reset")
        public SettingsDto resetSettings() {
            return settingsService.resetSettings();
        }


}
