package main;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/***
 * Represents the user's entire music library. Holds an array of all songs, represented as MusicItems, in a user's library.
 */
public class MusicLibrary implements Serializable{
   private ArrayList<Track> currentLibrary;
   private ArrayList<Track> unlinkedItems;

   private ArrayList<String> LibraryDirectories;

   public MusicLibrary(){
      this.currentLibrary = new ArrayList<>();
      this.unlinkedItems = new ArrayList<>();
      this.LibraryDirectories= new ArrayList<>();
   }

   public void deserializeMusicObjects(){
      ArrayList<Track> itemsToRemove = new ArrayList<>();
      unlinkedItems.clear();

      for(Track MI: currentLibrary){
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

   public void addMusic(Track Music){
      this.currentLibrary.add(Music);
   }

   public ArrayList<Track> getCurrentLibrary(){
      return this.currentLibrary;
   }

   public ArrayList<String> getCurrentDirectories(){
      return this.LibraryDirectories;
   }

   public boolean containsSong(Track Music){
      return currentLibrary.contains(Music);
   }

   public ArrayList<Track> getUnlinkedItems(){
      return this.unlinkedItems;
   }

}
