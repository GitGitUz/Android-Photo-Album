package com.example.cs213_android;

import java.io.*;
import java.io.Serializable;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Photo implements Serializable {

    private static final long serialVersionUID = -3995519831916037917L;
    private String caption;
    private ArrayList<Tag> tags;
    private Album album;
    private SerializablePhoto sPhoto;
    private SerializablePhoto thumbNail;

    /**
     * Second constructor for a photo that, when given an image, calls the super constructor and converts the image to a serializable photo via the methods in the
     * SerializablePhoto class.
     * @param image
     */
    public Photo(Image image, Image tN, Album a) {
        caption = "";
        tags = new ArrayList<Tag>();
        sPhoto = new SerializablePhoto();
        thumbNail = new SerializablePhoto();
        sPhoto.setPhoto(image);
        thumbNail.setPhoto(tN);
        album = a;
    }

    /**
     * Sets the caption of the photo to the input String.
     * @param caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Retrieves the caption of the photo.
     * @return String
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Adds the input tag, a type-value pair, to the list of tags.
     * @param t - the type of tag
     * @param v - the value of the tag
     */
    public void addToTags(String t, String v) {
        tags.add(new Tag(t, v));
    }

    /**
     * Edits the selected tag at the index in the list by overwriting its values to the input type-value pair.
     * @param tagIndex
     * @param t
     * @param v
     */
    public void editTag(int tagIndex, String t, String v) {
        tags.get(tagIndex).setType(t);
        tags.get(tagIndex).setValue(v);
    }

    /**
     * Deletes the tag at the selected index in the list of tags.
     * @param tagIndex
     */
    public void deleteTag(int tagIndex) {
        tags.remove(tagIndex);
    }

    /**
     * Retrieves the tag from the list of tags, at the given index.
     * @param tagIndex
     * @return
     */
    public Tag getTag(int tagIndex) {
        return tags.get(tagIndex);
    }

    /**
     * Retrieves the entire list of tags attached to the user
     * @return ArrayList<Tag>
     */
    public ArrayList<Tag> getTags(){
        return tags;
    }

    /**
     * Retrieves the serializable photo contained in the photo object
     * @return
     */
    public SerializablePhoto getSerializablePhoto() {
        return sPhoto;
    }

    /**
     * Retrieves the Image contained in the thumbnail
     * @return Image
     */
    public Image getThumbnailImage() {
        return thumbNail.getPhoto();
    }

    /**
     * Retrieves the actual Image from the serializable photo by converting it back to an image
     * @return Image
     */
    public Image getsPhotoImage() {
        return sPhoto.getPhoto();
    }

    /**
     * Returns the ImageView of the Image in the thumbnail SerializablePhoto
     * @return ImageView
     */
    public ImageView getImage() {
        ImageView i = new ImageView(thumbNail.getPhoto());
        return i;
    }

    /**
     * Retrieves the album this photo belongs to
     * @return Album
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * Sets the name of this photo's album
     * @param name
     */
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Returns the name of the photo's album
     * @return String
     */
    public String getAlbumName() {
        return album.getName();
    }

    /**
     * Removes the given tag from the photo's list of tags
     * @param tag
     */
    public void deleteTag(Tag tag) {
        tags.remove(tag);
    }

    /**
     * Checks if the given tag is the same as any tag in the photo's list of tags
     * @param toCheck
     * @return boolean
     */
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
