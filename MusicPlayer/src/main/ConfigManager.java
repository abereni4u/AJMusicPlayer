import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

                    // Get a valid directory from the user

    /**
     * isValidDirectory validates if a string is a directory.
     *
     * @param directory String representing a directory.
     * @return a boolean representing if the given string is a directory or not.
     */
    public static boolean isValidDirectory(String directory){
        try {
            Path userDirectoryPath = Paths.get(directory);
            return Files.isDirectory(userDirectoryPath);
        }
        catch (InvalidPathException e){
            return false;
        }
    }

                    // Scan directory for music files

                    // Create config folder and config file

                    // Add directory to config file

                    // Create MusicLibrary from files

                    // Serialize MusicLibrary

                    // Return updated MusicLibrary

}
