package com.example.cs213_android;

import java.io.*;
import java.io.Serializable;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Photo implements Serializable {

    private static final long serialVersionUID = 3;
    transient Bitmap image;
    private String caption;
    private Album album;
    private ArrayList<Tag> tags;

    public Photo(Bitmap image, Album album, String caption) {
        tags = new ArrayList<Tag>();
        this.image = image;
        this.caption = caption;
        this.album = album;
    }

    public void setImage(Bitmap image){
        this.image = image;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }

    public String getAlbumName() {
        return album.getName();
    }

    public Tag getTag(int tagIndex) {
        return tags.get(tagIndex);
    }

    public void addTag(String t, String v) {
        tags.add(new Tag(t, v));
    }

    public void editTag(int tagIndex, String t, String v) {
        tags.get(tagIndex).setType(t);
        tags.get(tagIndex).setValue(v);
    }

    public void deleteTag(int tagIndex) {
        tags.remove(tagIndex);
    }

    public void deleteTag(Tag tag) {
        tags.remove(tag);
    }

    public ArrayList<Tag> getTags(){
        return tags;
    }

    public boolean duplicateTag(Tag toCheck) {
        for(Tag t: tags) {
            if(t.getType().compareToIgnoreCase(toCheck.getType().trim()) == 0 && t.getValue().compareToIgnoreCase(toCheck.getValue().trim()) == 0) {
                System.out.println("Tags are the same");
                return true;
            }
        }
        return false;
    }
}
