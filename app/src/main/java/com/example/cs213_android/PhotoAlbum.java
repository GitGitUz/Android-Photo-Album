package com.example.cs213_android;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.content.Context;

import android.os.Bundle;

import java.util.ArrayList;

public class PhotoAlbum extends AppCompatActivity {

    final Context context = this;
    ListView albums;
    ArrayAdapter<Album> aAdapter;
    public static User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        usr = User.readData(this);
        if(usr == null ){
            usr = new User();
        }

        if(usr.albums == null){
            usr.albums = new ArrayList<Album>();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_list);
       // System.out.println(context.getFileStreamPath("albums.dat").getAbsolutePath());

    }
}
