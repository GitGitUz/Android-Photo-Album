package com.example.cs213_android;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    final Context searchContext = this;
    Album searchResults;
    int numResults;
    private GridView searchGV;
    PhotoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceStates){
        searchResults = PhotoAlbumActivity.aList.searchResults;

        super.onCreate(savedInstanceStates);
        setContentView(R.layout.search_photos);

        Toolbar toolbar = findViewById(R.id.toolbar_sp);
        TextView textView = toolbar.findViewById(R.id.toolbar_spTitle);

        if(searchResults == null) {
            numResults = 0;
        }else{
            numResults = searchResults.getPhotos().size();
        }

        textView.setText("Search Results: (" + searchResults.getNumPhotos() + ") photo(s) found");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchGV = findViewById(R.id.search_grid);
        searchGV.setSelection(0);
        adapter = new PhotoAdapter(searchContext, searchResults.getCaptions(), searchResults.getPhotos());
        searchGV.setAdapter(adapter);
    }
}
