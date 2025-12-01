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
     * @param userDirectoryPath Valid directory path containing music files.
     * @return an ArrayList of music file paths.
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

                    // Add directory to config file

                    // Create MusicLibrary from files

                    // Serialize MusicLibrary

                    // Return updated MusicLibrary

}
