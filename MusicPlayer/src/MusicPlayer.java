import java.io.*;
import java.nio.file.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class MusicPlayer {

    static final String CONFIG_FOLDER = "../testConfigFolder";
    static final Path CONFIG_FOLDER_PATH = Paths.get(CONFIG_FOLDER);
    static final Path CONFIG_FILE_PATH = Paths.get(CONFIG_FOLDER, "/configFile.txt");

    static MusicLibrary USER_LIBRARY = null;
    static final String USER_LIBRARY_PATH = "../testConfigFolder/MusicLbrary.dat" ;
    static ArrayList<Path> userDirectories;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Welcome!");

        // Check if config folder/file exists
        // Deserialize MusicLibrary and load MusicItems
        // Scan directories from config File
        // If new songs in directory add to MusicLibrary
        if(Files.exists(CONFIG_FOLDER_PATH)) {
            // Deserialize MusicLibrary
            deserializeLibrary();
            // Create an array to hold new songs found in Directories
            ArrayList<MusicItem> newSongs = new ArrayList<>();

            try (BufferedReader reader = Files.newBufferedReader(CONFIG_FILE_PATH)) {
                Stream<String> lines = reader.lines();

                lines.skip(2).forEach(x -> {
                    if(isValidDirectory(x)) {
                        Path configDirectoryEntry = Paths.get(x);
                        try {
                            ArrayList<Path> files = getMusicFiles(configDirectoryEntry);
                            for(Path musicPath: files){
                                MusicItem newMusicItem = new MusicItem(musicPath.toString());
                                boolean insideLibrary = USER_LIBRARY.containsSong(newMusicItem);
                                if(!(insideLibrary)){
                                   newSongs.add(newMusicItem);
                                   USER_LIBRARY.addMusic(newMusicItem);
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        throw new InvalidPathException(x, "This path is invalid.");
                    }
                });

                System.out.println(newSongs.size() + " songs added:");
                newSongs.forEach(x -> System.out.println(x.getTitle()));
                serializeLibrary();
            }
        }

        // Get user path
        else{
            Path userDirectoryPath = getUserPath();
            // initialize new music library
            USER_LIBRARY = new MusicLibrary();

            if(userDirectoryPath!= null) {
                // Create config folder and file
                createInitialConfigSetup(userDirectoryPath);
                // Get initial list of audio files from user directory
                ArrayList<Path> userMusic = getMusicFiles(userDirectoryPath);
                // Create a MusicItem from each audio file in directory and add MusicItem to MusicLibrary
                for (Path uPath : userMusic) {
                    MusicItem currentSong = new MusicItem(uPath.toString());
                    USER_LIBRARY.addMusic(currentSong);
                    System.out.println(currentSong.getTitle());
                }

                // Serialize the music library and save it to the config folder
                serializeLibrary();
            }
        }
        System.out.println("Program completed");
    }

    public static void serializeLibrary() throws IOException {
        FileOutputStream outStream = new FileOutputStream(USER_LIBRARY_PATH);
        ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
        objectOutputFile.writeObject(USER_LIBRARY);
    }

    public static void deserializeLibrary() throws IOException, ClassNotFoundException {
        FileInputStream inStream = new FileInputStream(USER_LIBRARY_PATH);
        ObjectInputStream inputFile = new ObjectInputStream(inStream) ;
        USER_LIBRARY = (MusicLibrary) inputFile.readObject();
        USER_LIBRARY.deserializeMusicObjects();
    }

    /***
     *
     * Create config folder/file at specific location on user's PC and add user's given directory to config file
     *
     * @param userDirectoryPath Directory containing user's music files
     * @throws IOException
     */
    public static void createInitialConfigSetup(Path userDirectoryPath) throws IOException {

        Files.createDirectory(CONFIG_FOLDER_PATH);
        Path configFile = Paths.get(CONFIG_FOLDER, "\\configFile.txt");

        try { // Create configFile.txt and append given user directory to it.
            Files.createFile(configFile);
            try (BufferedWriter writer = Files.newBufferedWriter(configFile)) {
                writer.write("Directories:\n" +
                                 "=========\n");
                writer.write(userDirectoryPath.toString());
            }
        } catch (FileAlreadyExistsException x) {
            System.err.format("file named %s" +
                    " already exists%n", configFile);
        } catch (IOException x) {
            // Some other sort of failure, such as permissions.
            System.err.format("createFile error: %s%n", x);
        }

    }

     /**
     * getMusicFiles returns an array of paths representing music files in a directory.
     * @param userDirectoryPath Valid directory path containing music files.
     * @return an ArrayList of music file paths.
     */
    public static ArrayList<Path> getMusicFiles(Path userDirectoryPath) throws IOException {


        ArrayList<Path> userMusic = new ArrayList<>(); // empty array for music files in

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
