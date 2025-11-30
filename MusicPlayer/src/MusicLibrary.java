import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/***
 * Represents the user's entire music library. Holds an array of all songs, represented as MusicItems, in a user's library.
 */
public class MusicLibrary implements Serializable{
   private ArrayList<MusicItem> currentLibrary;
   private ArrayList<MusicItem> unlinkedItems;

   private ArrayList<String> LibraryDirectories;

   public MusicLibrary(){
      this.currentLibrary = new ArrayList<>();
      this.unlinkedItems = new ArrayList<>();
      this.LibraryDirectories= new ArrayList<>();
   }

   public void deserializeMusicObjects(){
      ArrayList<MusicItem> itemsToRemove = new ArrayList<>();
      unlinkedItems.clear();

      for(MusicItem MI: currentLibrary){
         Path MIPath = Paths.get(MI.getPathString());

         boolean inDirectories = false;
         for(String directory : LibraryDirectories){
            if (MIPath.startsWith(directory)) {
               inDirectories = true;
               break;
            }
         }

         if(!inDirectories){
            itemsToRemove.add(MI);
         }
         else if(Files.exists(MIPath)){
            MI.setFilePath();
         }
         else {
            unlinkedItems.add(MI);
         }
      }
      if(itemsToRemove.size() >0)
         System.out.println(itemsToRemove.size() + " songs have been removed from the library.");

      currentLibrary.removeAll(itemsToRemove);
   }

   public void addDirectory(String userDirectory){
      this.LibraryDirectories.add(userDirectory);
   }

   public void addMusic(MusicItem Music){
      this.currentLibrary.add(Music);
   }

   public ArrayList<MusicItem> getCurrentLibrary(){
      return this.currentLibrary;
   }

   public ArrayList<String> getCurrentDirectories(){
      return this.LibraryDirectories;
   }

   public boolean containsSong(MusicItem Music){
      return currentLibrary.contains(Music);
   }

   public ArrayList<MusicItem> getUnlinkedItems(){
      return this.unlinkedItems;
   }

}
