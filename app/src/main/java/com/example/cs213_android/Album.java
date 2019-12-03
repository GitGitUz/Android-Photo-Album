package com.example.cs213_android;

import java.util.ArrayList;
import java.io.Serializable;

public class Album implements Serializable{
    private static final long serialVersionUID = 5041994841914890943L;
    private String name;
    private ArrayList<Photo> photos;

    public Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
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

    public ArrayList<Photo> getAlbumPhotos(){
        return photos;
    }

    public Image getThumbnailPhoto() {
        if(photos.isEmpty()) {
            return null;
        }
        return photos.get(0).getsPhotoImage();
    }

    public void addPhoto(Photo p) {
        photos.add(p);
    }

    public void deletePhoto(Photo p) {
        photos.remove(p);
    }

}
