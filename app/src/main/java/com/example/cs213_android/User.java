package com.example.cs213_android;

import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import android.content.Context;

public class User implements Serializable {

    private static final long serialVersionUID = 3998637753678218765L;
    public ArrayList<Album> albums;
    public static Album diskAlbumsData = new Album("results");
    public static final String dataFile = "albums.dat";

    public User() {
        albums = new ArrayList<Album>();
    }

    public ArrayList<Album> getAlbumList(){
        return albums;
    }

    public void addNewAlbum(String aName) {
        albums.add(new Album(aName));
    }

    public void deleteAlbum(Album album) {
        albums.remove(album);
    }

    public boolean duplicateAlbumName(String aName) {
        for(Album a : albums) {
            if(a.getName().toLowerCase().equals(aName.trim().toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public int getIndex(String albumName) {
        for(int i = 0; i < albums.size(); i++) {
            if(albums.get(i).getName().compareTo(albumName) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static User readData(Context context){
        User u = null;
        try{
            FileInputStream fis = context.openFileInput(dataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            u = (User) ois.readObject();

            if(u.albums == null){
                u.albums = new ArrayList<Album>();
            }
            fis.close();
            ois.close();

        }catch(FileNotFoundException e){
            return null;
        }catch(IOException e){
            return null;
        }catch(ClassNotFoundException e){
            return null;
        }catch(Exception e){
            return null;
        }
        return u;
    }

    public void writeData(Context context){
        ObjectOutputStream output;
        try{
            FileOutputStream fos = context.openFileOutput(dataFile, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();

        }
    }
}
