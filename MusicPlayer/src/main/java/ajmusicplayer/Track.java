package ajmusicplayer;

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
    private String location;
    private final String title;
    private final String artist;
    private final String album;
    private final int trackLength;
    private final String genre;
    private final String year;

    // ------------- GETTERS | SETTERS -------------- //
    public String getTitle(){
        return this.title;
    }
    public String getLocation(){ return this.location;}
    public String getArtist(){ return this.artist;}
    public String getAlbum(){ return this.album;}
    public String getGenre(){ return this.genre;}
    public String getYear(){ return this.year;}
    // ------------- GETTERS | SETTERS -------------- //


    public static Track createTrack(String pathString) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {

        Path trackFilePath = Paths.get(pathString);

        AudioFile f = AudioFileIO.read(trackFilePath.toFile());
        Tag tag = f.getTag();

        // ---------- UNCHANGEABLE TAGS HERE ---------- //

        AudioHeader permTags = f.getAudioHeader();
        int tagTrackLength = permTags.getTrackLength();

        // ---------- SET TAGS HERE ---------- //

        Track newTrack = new Track.Builder()
                .location(pathString)
                .trackLength(tagTrackLength)
                .title(tag.getFirst(FieldKey.TITLE))
                .artist(tag.getFirst(FieldKey.ARTIST))
                .album(tag.getFirst(FieldKey.ALBUM))
                .genre(tag.getFirst(FieldKey.GENRE))
                .year(tag.getFirst(FieldKey.YEAR))
                .build();

        return newTrack;
    }
    private Track(Builder builder){
        this.location   = builder.location;
        this.title      = builder.title;
        this.artist     = builder.artist;
        this.album      = builder.album;
        this.trackLength = builder.trackLength;
        this.genre      = builder.genre;
        this.year       = builder.year;
    }
    public static class Builder{
        private String location;
        private String title;
        private String artist;
        private String album;
        private int trackLength;
        private String genre;
        private String year;

        public Builder location(String location){
            this.location = location;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder artist(String artist) {
            this.artist = artist;
            return this;
        }
        public Builder album(String album) {
            this.album = album;
            return this;
        }
        public Builder trackLength(int trackLength) {
            this.trackLength = trackLength;
            return this;
        }
        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }
        public Builder year(String year) {
            this.year = year;
            return this;
        }
        public Track build() {
            return new Track(this);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof Track other)){
            return false;
        }

        if(this.location.equals("")){
            return false;
        }

        return this.location.equals(other.location);
    }
}