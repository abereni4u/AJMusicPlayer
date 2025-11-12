import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MusicPlayer {

    public static void main(String[] args){
        Scanner directoryScanner = new Scanner(System.in);
        System.out.println("Please enter a directory: ");
        String userDirectory = directoryScanner.nextLine();

        // Convert user's string into a path and test whether or not it's a valid directory.

        boolean isDirectory = isValidDirectory(userDirectory);
        String continueOrNot = "";

        while(!isDirectory){
           System.out.println("Please enter a valid directory.");
           System.out.println("Do you wish to continue? (Y/N) ");
           continueOrNot = directoryScanner.nextLine();
           if(continueOrNot.toLowerCase().equals("n"))
               break;
           System.out.println("Please enter a directory: ");
           userDirectory = directoryScanner.nextLine();
           isDirectory = isValidDirectory(userDirectory);
        }

        if(continueOrNot.equals("y") || (continueOrNot.equals(""))){
            System.out.println("You entered, " + userDirectory);
        }

        System.out.println("Program completed");

    }

    /**
     * isValidDirectory takes a strin
     * @param directory
     * @return
     */
    public static boolean isValidDirectory(String directory){
        Path userDirectoryPath = Paths.get(directory);
        return Files.isDirectory(userDirectoryPath);
    }
}
