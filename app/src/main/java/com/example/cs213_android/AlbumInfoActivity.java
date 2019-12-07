package com.example.cs213_android;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AlbumInfoActivity extends AppCompatActivity {
    final Context albumContext = this;
    private GridView photosGV;
    private Album album;
    private static final int PHOTO_TO_OPEN = 1;
    int option;
    TextViewAdapter adapter;

    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_info);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int index = getIntent().getIntExtra("index", 0);
        album = PhotoAlbumActivity.aList.albums.get(index);
        final int albumIndex = index;

        photosGV = findViewById(R.id.photo_grid);
        photosGV.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        photosGV.setSelection(0);
        adapter = new TextViewAdapter(this, album.getCaptions(), album.getPhotos());
        photosGV.setAdapter(adapter);



    }
}
