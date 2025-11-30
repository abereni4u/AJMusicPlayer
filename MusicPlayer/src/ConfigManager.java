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

                    // Scan directory for music files

                    // Create config folder and config file

                    // Add directory to config file

                    // Create MusicLibrary from files

                    // Serialize MusicLibrary

                    // Return updated MusicLibrary

}
