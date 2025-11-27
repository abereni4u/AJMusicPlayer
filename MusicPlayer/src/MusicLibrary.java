import java.io.Serializable;
import java.util.ArrayList;

/***
 * Represents the user's entire music library. Holds an array of all songs, represented as MusicItems, in a user's library.
 */
public class MusicLibrary implements Serializable{
   private ArrayList<MusicItem> currentLibrary;

   public MusicLibrary(){
      this.currentLibrary = new ArrayList<>();
   }

   public void deserializeMusicObjects(){
      for(MusicItem MI: currentLibrary){
         MI.setFilePath();
      }
   }

   public void addMusic(MusicItem Music){
      this.currentLibrary.add(Music);
   }

   public ArrayList<MusicItem> getCurrentLibrary(){
      return this.currentLibrary;
   }

   public boolean containsSong(MusicItem Music){
      return currentLibrary.contains(Music);
   }
}
