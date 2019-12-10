package com.example.cs213_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.text.InputType;

import android.util.Log;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhotoAlbumActivity extends AppCompatActivity {

    final Context mainContext = this;

    private ListView albumsLV;
    public static AlbumsList aList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aList = new AlbumsList();
        aList.albums = AlbumsList.readData(this);


        if (aList == null) {
            Log.i("Albums List Object is", "null");
            aList = new AlbumsList();
            aList.albums.add(new Album("Album1"));
            aList.albums.add(new Album("Album2"));
            AlbumsList.writeData(this, aList.getAlbumList());
            Log.i("AAlbum1 Name ", aList.albums.get(0).getName());
            Log.i("AAlbum2 Name ", aList.albums.get(1).getName());
        } else {
            Log.i("Albums list Object is", "not null");
        }

        if (aList.albums == null) {
            Log.i("Albums list is", "null");
            aList.albums = new ArrayList<Album>();
            aList.albums.add(new Album("Album1"));
            aList.albums.add(new Album("Album2"));
            AlbumsList.writeData(this, aList.getAlbumList());
            Log.i("AAlbum1 Name ", aList.albums.get(0).getName());
            Log.i("AAlbum2 Name ", aList.albums.get(1).getName());
        } else {
            Log.i("Albums list is", "not null");
        }

        if (aList.albums.isEmpty()) {
            Log.i("Albums list is", "empty");
            aList.albums.add(new Album("Album1"));
            aList.albums.add(new Album("Album2"));
            AlbumsList.writeData(this, aList.getAlbumList());
            Log.i("EAlbum1 Name ", aList.albums.get(0).getName());
            Log.i("EAlbum2 Name ", aList.albums.get(1).getName());
        } else {
            Log.i("Albums list is", "not empty");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_display);

        albumsLV = findViewById(R.id.albums_list);
        albumsLV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albumsLV.setSelection(0);

        ArrayAdapter<Album> albumAdapter = new ArrayAdapter<Album>(this, R.layout.album, aList.albums);
        albumsLV.setAdapter(albumAdapter);


        FloatingActionButton add = findViewById(R.id.addAlbum);
        FloatingActionButton search = findViewById(R.id.searchPhotos);
        FloatingActionButton rename = findViewById(R.id.renameAlbum);
        FloatingActionButton delete = findViewById(R.id.deleteAlbum);
        FloatingActionButton cancel = findViewById(R.id.cancelA);

        add.setOnClickListener((View v)->{
            AlertDialog.Builder ab = new AlertDialog.Builder(mainContext);
            ab.setTitle("Enter name for the new album");

            final EditText inp = new EditText(mainContext);
            inp.setInputType(InputType.TYPE_CLASS_TEXT);
            ab.setView(inp);

            ab.setPositiveButton("Apply", ((DialogInterface dialog, int which) -> {
                String newName = inp.getText().toString();
                if(aList.duplicateAlbumName(newName.trim().toLowerCase())){
                    AlertDialog.Builder dupe = new AlertDialog.Builder(mainContext);
                    dupe.setTitle("Duplicate Album Name");
                    dupe.setMessage("An album with that name already exists");
                    dupe.setPositiveButton("OK", ((DialogInterface di, int d) -> {
                        di.dismiss();
                        delete.hide();
                        rename.hide();
                        cancel.hide();
                        add.show();
                        search.show();
                        return;
                    }));
                    dupe.show();
                }else if(newName.trim().isEmpty()){
                    AlertDialog.Builder dupe = new AlertDialog.Builder(mainContext);
                    dupe.setTitle("Invalid Input");
                    dupe.setMessage("New album name cannot be blank");
                    dupe.setPositiveButton("OK", ((DialogInterface di, int d) -> {
                        di.dismiss();
                        delete.hide();
                        rename.hide();
                        cancel.hide();
                        add.show();
                        search.show();
                        return;
                    }));
                    dupe.show();
                }else{
                    aList.addAlbum(newName);
                    AlbumsList.writeData(mainContext, aList.getAlbumList());
                    albumsLV.setAdapter(albumAdapter);
                    delete.hide();
                    rename.hide();
                    cancel.hide();
                    add.show();
                    search.show();
                }
            }));

            ab.setNegativeButton("Cancel", ((DialogInterface dialog, int which) -> {
                dialog.cancel();
            }));
            ab.show();
        });

        search.setOnClickListener((View v)->{
            final Dialog dialog = new Dialog(mainContext);
            dialog.setContentView(R.layout.search_dialog);
            dialog.setTitle("Provide tag-value pair");

            Button searchBtn = dialog.findViewById(R.id.searchBtn);
            Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
            Spinner searchSpinner = dialog.findViewById(R.id.spinner_search);
            AutoCompleteTextView autoText = dialog.findViewById(R.id.acTV);
            final ArrayList<String> lTags = new ArrayList<String>();
            final ArrayList<String> pTags = new ArrayList<String>();

            for(int i = 0; i < PhotoAlbumActivity.aList.getAlbumList().size(); i++){
                for(int j = 0; j < PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().size(); j++){
                    if(PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().get(j).getLocationTags() == null){
                        continue;
                    }
                    lTags.addAll(PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().get(j).getLocationTags());
                }
            }

            for(int i = 0; i < PhotoAlbumActivity.aList.getAlbumList().size(); i++){
                for(int j = 0; j < PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().size(); j++){
                    if(PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().get(j).getPersonTags() == null){
                        continue;
                    }
                    pTags.addAll(PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().get(j).getPersonTags());
                }
            }

            searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(searchSpinner.getSelectedItem().toString().trim().equals("location")){
                        ArrayAdapter<String> autoAdapter = new ArrayAdapter<String>(mainContext, android.R.layout.simple_dropdown_item_1line, lTags);
                        autoText.setAdapter(autoAdapter);
                    }else{
                        ArrayAdapter<String> autoAdapter = new ArrayAdapter<String>(mainContext, android.R.layout.simple_dropdown_item_1line, pTags);
                        autoText.setAdapter(autoAdapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            searchBtn.setOnClickListener((View view)->{
                aList.searchResults.clearSearchResults();
                String tagType = searchSpinner.getSelectedItem().toString().trim();
                String tagValue = autoText.getText().toString().trim();

                if(tagValue.isEmpty()){
                    AlertDialog.Builder ab = new AlertDialog.Builder(mainContext);
                    ab.setTitle("Input error");
                    ab.setMessage("Please enter a tag value");
                    ab.setPositiveButton("OK", ((DialogInterface d, int which)->{
                        dialog.dismiss();
                    }));
                    ab.show();
                }else{
                    if(tagType.equals("location")){
                        for(int i = 0; i < PhotoAlbumActivity.aList.getAlbumList().size(); i++){
                            for(int j = 0; j < PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().size(); j++){
                                ArrayList<String> l = PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().get(j).getLocationTags();
                                if(l == null){
                                    break;
                                }
                                if(l.isEmpty()){
                                    break;
                                }
                                if(l.contains(tagValue)){
                                    aList.searchResults.addPhoto(aList.getAlbumList().get(i).getPhotos().get(j));
                                }else{
                                    for(int k = 0; k < l.size(); k++){
                                        if(l.get(k).startsWith(tagValue)){
                                            aList.searchResults.addPhoto(aList.getAlbumList().get(i).getPhotos().get(j));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        for(int i = 0; i < PhotoAlbumActivity.aList.getAlbumList().size(); i++){
                            for(int j = 0; j < PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().size(); j++){
                                ArrayList<String> p = PhotoAlbumActivity.aList.getAlbumList().get(i).getPhotos().get(j).getPersonTags();
                                if(p == null){
                                    break;
                                }
                                if(p.isEmpty()){
                                    break;
                                }
                                if(p.contains(tagValue)){
                                    aList.searchResults.addPhoto(aList.getAlbumList().get(i).getPhotos().get(j));
                                }else{
                                    for(int k = 0; k < p.size(); k++){
                                        if(p.get(k).startsWith(tagValue)){
                                            aList.searchResults.addPhoto(aList.getAlbumList().get(i).getPhotos().get(j));
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                if(aList.searchResults.getNumPhotos().equals("0")){
                    AlertDialog.Builder ab = new AlertDialog.Builder(mainContext);
                    ab.setTitle("Search failed");
                    ab.setMessage("No pictures found");
                    ab.setPositiveButton("OK", ((DialogInterface d, int which)->{
                        dialog.dismiss();
                    }));
                    ab.show();
                }else{
                    Intent intent = new Intent(PhotoAlbumActivity.this, SearchActivity.class);
                    intent.putExtra("index", 0);
                    startActivity(intent);
                }


            });

            cancelBtn.setOnClickListener((View view)->{
                dialog.dismiss();
            });

            dialog.show();
        });

        albumsLV.setOnItemLongClickListener((AdapterView<?> av, View view, int pos, long id) -> {
            final int pos2 = pos;
            albumsLV.requestFocusFromTouch();
            albumsLV.setSelection(pos2);

            delete.show();
            rename.show();
            cancel.show();
            search.hide();
            add.hide();

            rename.setOnClickListener((View v)->{
                AlertDialog.Builder ab = new AlertDialog.Builder(mainContext);
                ab.setTitle("Enter a new name for this album");

                final EditText inp = new EditText(mainContext);
                inp.setInputType(InputType.TYPE_CLASS_TEXT);
                ab.setView(inp);

                ab.setPositiveButton("Apply", ((DialogInterface dialog, int which) -> {
                   String newName = inp.getText().toString();

                   if(aList.duplicateAlbumName(newName.trim().toLowerCase())){
                       AlertDialog.Builder dupe = new AlertDialog.Builder(mainContext);
                       dupe.setTitle("Duplicate Album Name");
                       dupe.setMessage("An album with that name already exists");
                       dupe.setPositiveButton("OK", ((DialogInterface di, int d) -> {
                           di.dismiss();
                           delete.hide();
                           rename.hide();
                           cancel.hide();
                           add.show();
                           search.show();
                           return;
                       }));
                       dupe.show();
                   }else if(newName.trim().isEmpty()){
                       AlertDialog.Builder dupe = new AlertDialog.Builder(mainContext);
                       dupe.setTitle("Invalid Input");
                       dupe.setMessage("New album name cannot be blank");
                       dupe.setPositiveButton("OK", ((DialogInterface di, int d) -> {
                           di.dismiss();
                           delete.hide();
                           rename.hide();
                           cancel.hide();
                           add.show();
                           search.show();
                           return;
                       }));
                       dupe.show();
                   }else{
                       aList.getAlbumList().get(pos2).setName(newName);
                       AlbumsList.writeData(mainContext, aList.getAlbumList());
                       albumsLV.setAdapter(albumAdapter);
                       delete.hide();
                       rename.hide();
                       cancel.hide();
                       add.show();
                       search.show();
                   }
                }));

                ab.setNegativeButton("Cancel", ((DialogInterface dialog, int which) -> {
                    rename.hide();
                    cancel.hide();
                    delete.hide();
                    search.show();
                    add.show();
                    dialog.cancel();
                }));
                ab.show();
            });

            delete.setOnClickListener((View v)-> {
                AlertDialog.Builder ab = new AlertDialog.Builder(mainContext);
                ab.setTitle("Do you want to delete this album?");

                ab.setPositiveButton("Yes", ((DialogInterface dialog, int which) -> {
                    aList.removeAlbum(pos2);
                    AlbumsList.writeData(mainContext, aList.getAlbumList());
                    albumsLV.setAdapter(albumAdapter);
                    delete.hide();
                    rename.hide();
                    cancel.hide();
                    add.show();
                    search.show();
                }));

                ab.setNegativeButton("Cancel", ((DialogInterface dialog, int which) -> {
                    delete.hide();
                    rename.hide();
                    cancel.hide();
                    search.show();
                    add.show();
                }));

                ab.show();
            });

            cancel.setOnClickListener((View v) -> {
                Toast returning = Toast.makeText(mainContext, "Returning to 'Your Albums'", Toast.LENGTH_SHORT);
                returning.show();
                delete.hide();
                rename.hide();
                cancel.hide();
                add.show();
                search.show();
            });
            return true;
        });

        albumsLV.setOnItemClickListener((AdapterView<?> av, View view, int pos, long id) -> {
                    Intent intent = new Intent(PhotoAlbumActivity.this, AlbumInfoActivity.class);
                    intent.putExtra("index",pos);
                    startActivity(intent);
        });

    }
}



