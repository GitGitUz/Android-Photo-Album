package com.example.cs213_android;

import java.util.ArrayList;
import java.io.Serializable;

public class Album implements Serializable{
    private static final long serialVersionUID = 2L;
    private String name;
    private ArrayList<Photo> photos;

    public Album(String name) {
        this.name = name;
        photos = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNumPhotos() {
        return ""+photos.size();
    }

    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    public ArrayList<String> getCaptions(){
        ArrayList<String> captions = new ArrayList<String>();
        for(Photo p : photos){
            captions.add(p.getCaption());
        }
        return captions;
    }

    public void addPhoto(Photo p) {
        photos.add(p);
    }

//    public void deletePhoto(Photo p) {
//        photos.remove(p);
//    }

    public String toString(){
        return this.getName();
    }

}
