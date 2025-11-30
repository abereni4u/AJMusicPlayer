package tests;

import main.ConfigManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

    @Test
    @DisplayName("Validate if string is directory")
    void isValidDirectory() {
        assertAll(
                () -> assertFalse(ConfigManager.isValidDirectory("P:/mcus")),
                () -> assertFalse(ConfigManager.isValidDirectory("")),
                () -> assertFalse(ConfigManager.isValidDirectory("P:/mu   Si  c")),
                () -> assertFalse(ConfigManager.isValidDirectory("$#$#$/ADSA/1q23as//asda\\asd\\asd")),

                () -> assertTrue(ConfigManager.isValidDirectory("P:/muSic")),
                () -> assertTrue(ConfigManager.isValidDirectory("C:\\Users\\Work Account\\Desktop\\WorkRepos\\AJMusicPlayer\\testMusicFolder")),
                () -> assertTrue(ConfigManager.isValidDirectory("../testMusicFolder"))
        );
    }
}