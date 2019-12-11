package com.example.cs213_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);

        toolbar.setNavigationOnClickListener((View v)->{
            Intent intent = new Intent(SearchActivity.this, PhotoAlbumActivity.class);
            startActivity(intent);
        });

        TextView textView = toolbar.findViewById(R.id.toolbar_spTitle);
        textView.setText("Search Results: (" + searchResults.getNumPhotos() + ") photo(s) found");

        if(searchResults == null) {
            numResults = 0;
        }else{
            numResults = searchResults.getPhotos().size();
        }

        searchGV = findViewById(R.id.search_grid);
        searchGV.setSelection(0);
        adapter = new PhotoAdapter(searchContext, searchResults.getCaptions(), searchResults.getPhotos());
        searchGV.setAdapter(adapter);
    }
}
