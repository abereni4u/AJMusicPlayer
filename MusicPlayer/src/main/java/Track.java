import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a song in the user's library. Holds filepath and metadata as well as methods to retrieve those
 * fields
 */
public class Track implements Serializable {
    private transient Path filePath;
    private final String title;

    private final String artist;

    private final String album;

    private final int trackLength;

    private String pathString = "";

    public static Track createTrack(String pathString) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {

        Path trackFilePath = Paths.get(pathString);


        AudioFile f = AudioFileIO.read(trackFilePath.toFile());
        Tag tag = f.getTag();
        AudioHeader permTags = f.getAudioHeader(); // Tags that can't be changed here

        int tagTrackLength = permTags.getTrackLength();
        String tagTitle = tag.getFirst(FieldKey.TITLE);
        String tagArtist = tag.getFirst(FieldKey.ARTIST);
        String tagAlbum = tag.getFirst(FieldKey.ALBUM);

        return new Track(tagTrackLength, tagTitle, tagArtist, tagAlbum);
    }

    private Track(int trackLength, String title, String artist, String album){
        this.trackLength = trackLength;
        this.title = title;
        this.artist = artist;
        this.album = album;
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

        if(!(obj instanceof Track other)){
            return false;
        }
        if(this.filePath == null){
            return this.pathString.equals(other.pathString);
        }

        return this.filePath.equals(other.filePath);
    }
}