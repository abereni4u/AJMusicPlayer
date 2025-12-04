package tests;

import main.ConfigManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

    /***
     * loadTestFiles creates test files of random types at a given directory, using extensions in the allFileTypes array.
     * @param directory the desired directory to place test files
     * @param count the number of files to create in the given directory
     * @param allFileTypes an array of extensions to randomly append to each text file.
     * @throws IOException if an I/O error occurs.
     */
    void loadTestFiles(Path directory, int count,  String[] allFileTypes) throws IOException {
       Random rand = new Random();
       String tempMusicPathString;
        for(int i = 0; i <= count; i++) {
            tempMusicPathString = "testfile" + i;
            Files.createTempFile(directory, tempMusicPathString, allFileTypes[rand.nextInt(allFileTypes.length)]);
        }
    }

    @TempDir
    Path aTempDir;

    // --------------------------------------------
    //           isValidDirectory Tests          //
    // --------------------------------------------
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

    // --------------------------------------------
    //           getMusicFiles Tests             //
    // --------------------------------------------

    @Test
    @DisplayName("Tests getMusicFiles on a directory full of ONLY music files")
    void getMusicFiles_onlyAudio() throws IOException {
        String tempMusicPathString = "";
        String[] fileTypes = {".mp3"};

        loadTestFiles(aTempDir, 10, fileTypes);

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

    @Test
    @DisplayName("Tests getMusicFiles on a directory with files, but not audio files")
    void getMusicFiles_noMusicFiles() throws IOException {
        String tempMusicPathString = "";

        String[] fileTypes = {".txt", ".xml", ".c", ".pdf", ".word", ".py"};
        loadTestFiles(aTempDir, 10, fileTypes);

        ArrayList<Path> musicFileArray = ConfigManager.getMusicFiles(aTempDir);
        assertEquals(0, musicFileArray.size());

    }

    // --------------------------------------------
    //           createConfigFolder              //
    // --------------------------------------------

    @Test
    @DisplayName("Tests createConfigFolder in a directory where 'config' does not exist")
    void createConfigFolder_noDirectoryExists(){
        ConfigManager configM = new ConfigManager();
        assertTrue(configM.createConfigFolder(aTempDir));
        Path configFolderPath = aTempDir.resolve("config");
        assertTrue(Files.isDirectory(configFolderPath));
    }
    @Test
    @DisplayName("Tests createConfigFolder in a directory where 'config' exists")
    void createConfigFolder_DirectoryExists() throws IOException {
        ConfigManager configM = new ConfigManager();
        Path configFolderPath = aTempDir.resolve("config");
        Files.createDirectories(configFolderPath);
        assertTrue(configM.createConfigFolder(aTempDir));
        assertTrue(Files.isDirectory(configFolderPath));
    }
    @Test
    @DisplayName("Tests createConfigFolder in a directory where file named 'config' exists")
    void createConfigFolder_sameFileNameExists() throws IOException {
        ConfigManager configM = new ConfigManager();
        Path configFolderPath = aTempDir.resolve("config");
        Files.createFile(configFolderPath);
        assertFalse(configM.createConfigFolder(aTempDir));
    }


    // --------------------------------------------
    //           createConfigFile                //
    // --------------------------------------------
    @Test
    @DisplayName("Tests createConfigFile in a directory where 'config.txt' does not exist")
    void createConfigFile_noFileExists() throws IOException {
        ConfigManager configM = new ConfigManager();
        boolean createdFile = configM.createConfigFile(aTempDir);
        Path configFilePath = aTempDir.resolve("config.txt");
        assertTrue(createdFile);
        assertTrue(Files.exists(aTempDir));
    }

}