import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class MusicPlayer {

    public static void main(String[] args) throws IOException {

        // Test if user's string is a valid directory and returns a Path object if it is.
        Path userDirectoryPath = getUserPath();

        // Iterate through the files in the directory and add all music files present to an array.
        if (userDirectoryPath!= null) {
           ArrayList<Path> userMusic = getMusicFiles(userDirectoryPath);
           for(Path uPath : userMusic ){
               System.out.println(uPath.getFileName());
            }
        }
        System.out.println("Program completed");
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
