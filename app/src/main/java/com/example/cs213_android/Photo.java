package com.example.cs213_android;

import java.io.*;
import java.io.Serializable;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Photo implements Serializable {

    private static final long serialVersionUID = 3L;
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

    public Bitmap getImage(){
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public void addTag(Tag t){
        tags.add(t);
    }

    public ArrayList<Tag> getTags(){
        return tags;
    }

    public ArrayList<String> getTagsAsStrings(){
        ArrayList<String> tagStrings = new ArrayList<String>();
        for(Tag t : tags){
            tagStrings.add(t.toString());
        }
        return tagStrings;
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

    public ArrayList<String> getLocationTags(){
        ArrayList<String> locTags = new ArrayList<String>();
        for(Tag t : tags){
            if(t.getType().equalsIgnoreCase("location")){
                locTags.add(t.getValue());
            }
        }
        return locTags;
    }

    public ArrayList<String> getPersonTags(){
        ArrayList<String> personTags = new ArrayList<String>();
        for(Tag t : tags){
            if(t.getType().equalsIgnoreCase("person")){
                personTags.add(t.getValue());
            }
        }
        return personTags;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        int bite;
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        while((bite = ois.read()) != -1){
            baos.write(bite);
        }

        byte picBMBytes[] = baos.toByteArray();
        image = BitmapFactory.decodeByteArray(picBMBytes, 0, picBMBytes.length);
    }

    private void writeObject(ObjectOutputStream oos) throws Exception{
        oos.defaultWriteObject();
        if(image != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, baos);
            byte picBMBytes[] = baos.toByteArray();
            oos.write(picBMBytes, 0, picBMBytes.length);
        }
    }


}
