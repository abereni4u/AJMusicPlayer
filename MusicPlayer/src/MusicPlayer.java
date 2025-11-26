import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class MusicPlayer {

    static final String CONFIG_FOLDER = "C:\\Users\\Work Account\\Desktop\\WorkRepos\\AJMusicPlayer\\testConfigFolder";
    static final Path CONFIG_FOLDER_PATH = Paths.get(CONFIG_FOLDER);

    static final Path CONFIG_FILE_PATH = Paths.get(CONFIG_FOLDER, "\\configFile.txt");
    public static void main(String[] args) throws IOException {

        // Check if config folder/file exists
        // If it does: load songs from directories in file
        if(Files.exists(CONFIG_FOLDER_PATH)) {
            Path newDirectory = getUserPath();
            assert newDirectory != null;
            String newDirectoryString = newDirectory.toString();
            try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE_PATH, StandardOpenOption.APPEND)) {
                writer.write(newDirectoryString + "\n");
            }
        }
        // Test if user's string is a valid directory and returns a Path object if it is.
        else{

            Path userDirectoryPath = getUserPath();

            // Create config folder/file at specific location on user's PC and add user's given directory to config file
            // Iterate through the files in the given directory and add all music files present to an array
            if (userDirectoryPath!= null) {
                // Create confing folder
                Files.createDirectory(CONFIG_FOLDER_PATH);
                Path configFile = Paths.get(CONFIG_FOLDER, "\\configFile.txt");

                try{ // Create configFile.txt and append given user directory to it.
                    Files.createFile(configFile);
                    try (BufferedWriter writer = Files.newBufferedWriter(configFile)) {
                        writer.write(userDirectoryPath.toString());
                    }
                }
                catch (FileAlreadyExistsException x){
                    System.err.format("file named %s" +
                            " already exists%n", configFile);
                }
                catch (IOException x){
                    // Some other sort of failure, such as permissions.
                    System.err.format("createFile error: %s%n", x);
                }


                ArrayList<Path> userMusic = getMusicFiles(userDirectoryPath);
                for(Path uPath : userMusic ){
                    System.out.println(uPath.getFileName());
                }
            }
            System.out.println("Program completed");
        }

    }

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

    /**
     * getUserPath returns a valid Path object based of user input. If the user terminates the prompt before a
     * valid path can be confirmed, null is returned instead.
     * @return a Path object if given user directory is valid, or null if user chooses to terminate early.
     */
    public static Path getUserPath(){
        Scanner directoryScanner = new Scanner(System.in);
        System.out.println("Please enter a directory: ");
        String userDirectory = directoryScanner.nextLine();

        // Check if the user's input is a valid director
        boolean isDirectory = isValidDirectory(userDirectory);
        String continueOrNot = "";

        // While the directory is not valid, continuously prompt user to provide a new directory.
        while(!isDirectory){
            System.out.println("Please enter a valid directory.");
            System.out.println("Do you wish to continue? (Y/N) ");
            continueOrNot = directoryScanner.nextLine();
            if(continueOrNot.equalsIgnoreCase("n"))
                break;
            System.out.println("Please enter a directory: ");
            userDirectory = directoryScanner.nextLine();
            isDirectory = isValidDirectory(userDirectory);
        }

        // If user chooses to terminate program early return null instead of a Path object.
        if(continueOrNot.equalsIgnoreCase("n")){
            return null;
        }
        return Paths.get(userDirectory);
    }

    /**
     * isValidDirectory takes a string and tests if it's a valid directory. Returns true if the string is a directory,
     * false otherwise.
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
}
