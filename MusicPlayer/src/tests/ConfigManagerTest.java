package tests;

import main.ConfigManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

    @TempDir
    Path aTempDir;

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

    @Test
    @DisplayName("Tests getMusicFiles on a directory full of ONLY music files")
    void getMusicFiles_onlyAudio() throws IOException {
        String tempMusicPathString = "";
        for(int i = 0; i <= 10; i++) {
            tempMusicPathString = "testfile" + i;
            Files.createTempFile(aTempDir, tempMusicPathString, ".mp3");
        }

        ArrayList<Path> musicFileArray = ConfigManager.getMusicFiles(aTempDir);
        for(Path tempMusicPath : musicFileArray){
            assertEquals("audio/mpeg", Files.probeContentType(tempMusicPath));
        }
    }

    @Test
    @DisplayName("Tests getMusicFiles on a directory with no files")
    void getMusicFiles_emptyDirectory() throws IOException {

        ArrayList<Path> musicFileArray = ConfigManager.getMusicFiles(aTempDir);
        assertEquals(0, musicFileArray.size());
    }


}