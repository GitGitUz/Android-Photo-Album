package com.example.cs213_android;

import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import android.content.Context;

public class AlbumsList implements Serializable {

    private static final long serialVersionUID = 1L;
    public static ArrayList<Album> albums;
    public static final String dataFile = "albums.dat";
    public AlbumsList() {
        albums = new ArrayList<Album>();
    }

    public ArrayList<Album> getAlbumList(){
        return albums;
    }

    public void addNewAlbum(String aName) {
        albums.add(new Album(aName));
    }

    public static ArrayList<Album> readData(Context context){
        ArrayList<Album> albms = new ArrayList<Album>();
        try {
            FileInputStream fis = context.openFileInput(dataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object temp = null;
            while((temp = ois.readObject())!= null){
                if(temp instanceof  Album){
                    Album a = (Album)temp;
                    albms.add(a);
                }else{
                    System.err.println("Class: " + temp.getClass().toString() + " not expected.");
                    return null;
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return albms;
    }


    public static void writeData(Context context, ArrayList<Album> albumList){
        try{
            FileOutputStream fos = context.openFileOutput(dataFile, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Album a : albumList) {
                oos.writeObject(a);
            }
            oos.close();
            fos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void initializeFromData(){
    }
}
