import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MusicPlayer {

    public static void main(String[] args) throws IOException {
        Scanner directoryScanner = new Scanner(System.in);
        System.out.println("Please enter a directory: ");
        String userDirectory = directoryScanner.nextLine();

        ArrayList<Path> userMusic = new ArrayList<>();

        // Convert user's string into a path and test whether it's a valid directory.

        boolean isDirectory = isValidDirectory(userDirectory);
        String continueOrNot = "";

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

        if(continueOrNot.equals("y") || (continueOrNot.equals(""))){
            System.out.println("You entered, " + userDirectory);
        }

        // At this point we've confirmed the user's directory is valid
        Path userDirectoryPath = Paths.get(userDirectory);
        try (DirectoryStream<Path> userDirectoryEntries = Files.newDirectoryStream(userDirectoryPath)) {
            for (Path userDirectoryEntry : userDirectoryEntries) {
                String fileType = Files.probeContentType(userDirectoryEntry);
                if(fileType != null && fileType.split("/")[0].equals("audio")){
                    userMusic.add(userDirectoryEntry);
                }
            }
        }

        for(Path aPath : userMusic){
            System.out.println(aPath.getFileName());
        }

        System.out.println("Program completed");

    }

    /**
     * isValidDirectory takes a string
     * @param directory
     * @return
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
