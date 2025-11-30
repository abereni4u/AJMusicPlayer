import ConfigManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

    @Test
    @DisplayName("Validate if string is directory")
    void isValidDirectory() {
        assertAll(
                () -> assertFalse(ConfigManager.isValidDirectory("P:/mcus")),
                () -> assertTrue(ConfigManager.isValidDirectory("P:/muSic")),
                () -> assertFalse(ConfigManager.isValidDirectory(""))
        );
    }
}