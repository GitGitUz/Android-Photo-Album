package com.example.cs213_android;

import java.util.ArrayList;
import java.io.Serializable;

public class Album implements Serializable{
    private static final long serialVersionUID = 2L;
    private String name;
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    public Album(String name) {
        this.name = name;
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

    public void clearSearchResults(){
        photos.clear();
    }

    public String toString(){
        return this.getName();
    }

}
