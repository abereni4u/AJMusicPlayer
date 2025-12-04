package main;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class ConfigManager {

    // Return a MusicLibrary object

        // Check if config folder exists

            // If it exists:

                    // Read directories from config file

                    // Deserialize MusicLibrary
                        // Update MusicLibrary
                            // Check for unlinks and deletions

                    // Serialize MusicLibrary

                    // Return updated MusicLibrary object

            // If it doesn't exist:

                    // Get a valid directory from the userr

    /**
     * isValidDirectory validates if a string is a directory.
     *
     * @param directory String representing a directory.
     * @return a boolean representing if the given string is a directory or not.
     */
    public static boolean isValidDirectory(String directory){
        if(directory.equals(""))
            return false;
        try {
            Path userDirectoryPath = Paths.get(directory);
            return Files.isDirectory(userDirectoryPath);
        }
        catch (InvalidPathException e){
            return false;
        }
    }

                    // Scan directory for music files
    /**
     * getMusicFiles returns an array of paths representing music files in a directory.
     * If no music files are detected, the array will be of length 0.
     * @param userDirectoryPath Valid directory path containing music files.
     * @return an ArrayList of music file paths OR empty if no music files are detected.
     */
    public static ArrayList<Path> getMusicFiles(Path userDirectoryPath) throws IOException {

        ArrayList<Path> userMusic = new ArrayList<>();

        // Create a directory stream and iterate through its entries
        try (DirectoryStream<Path> userDirectoryEntries = Files.newDirectoryStream(userDirectoryPath)) {
            for (Path userDirectoryEntry : userDirectoryEntries) {
                // For each entry in directory determine its file type and place in userMusic array if it's an
                // audio file.
                String fileType = Files.probeContentType(userDirectoryEntry);
                // null in case file type is indeterminate
                if (fileType != null && fileType.split("/")[0].equals("audio")) {
                    userMusic.add(userDirectoryEntry);
                }
            }
        }
        return userMusic;
    }

                    // Create config folder and config file

    /***
     * createConfigFolder creates a folder named "config" at the given directory.
     * Precondition: configDirectory is a valid directory, and a file named "config" does not already exist.
     * Postcondition: a folder named "config" is created at the given directory.
     * @param configDirectory the desired directory at which to create a config folder.
     * @return true if directory creation was successful, false otherwise.
     */
    public boolean createConfigFolder(Path configDirectory) {
        try {
            Path configPath = configDirectory.resolve("config");
            Files.createDirectories(configPath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public boolean createConfigFile(Path configFolder) throws IOException {
        try {
            Files.createFile(configFolder.resolve("config.txt"));
            return true;
        }
        catch(FileAlreadyExistsException e){
            return false;
        }
    }
                    // Add directory to config file

                    // Create MusicLibrary from files

                    // Serialize MusicLibrary

                    // Return updated MusicLibrary

}
