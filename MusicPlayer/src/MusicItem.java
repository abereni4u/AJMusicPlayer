import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a song in the user's library. Holds filepath and metadata as well as methods to retrieve those
 * fields
 */
public class MusicItem implements Serializable {
    private transient Path filePath;
    private final String title;

    private String pathString = "";

    public MusicItem(String pathString) {
        this.pathString = pathString;
        this.filePath = Paths.get(pathString);
        this.title = String.valueOf(filePath.getFileName());
    }

    public Path getFilePath(){
        return filePath;
    }

    public String getTitle(){
        return this.title;
    }

    public String getPathString(){ return this.pathString;}

    public void setFilePath(){
        this.filePath = Paths.get(pathString);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof MusicItem other)){
            return false;
        }
        if(this.filePath == null){
            return this.pathString.equals(other.pathString);
        }

        return this.filePath.equals(other.filePath);
    }
}