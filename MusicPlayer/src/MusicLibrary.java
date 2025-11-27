import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/***
 * Represents the user's entire music library. Holds an array of all songs, represented as MusicItems, in a user's library.
 */
public class MusicLibrary implements Serializable{
   private ArrayList<MusicItem> currentLibrary;
   private ArrayList<MusicItem> unlinkedItems;

   public MusicLibrary(){
      this.currentLibrary = new ArrayList<>();
      this.unlinkedItems = new ArrayList<>();
   }

   public void deserializeMusicObjects(){
      for(MusicItem MI: currentLibrary){
         if(Files.exists(Paths.get(MI.getPathString()))){
            MI.setFilePath();
            unlinkedItems.remove(MI);
         }
         else {
            unlinkedItems.add(MI);
         }
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

   public ArrayList<MusicItem> getUnlinkedItems(){
      return this.unlinkedItems;
   }
}
