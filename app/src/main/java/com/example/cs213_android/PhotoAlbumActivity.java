package com.example.cs213_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.DialogInterface;

import android.text.InputType;

import android.util.Log;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.TextView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhotoAlbumActivity extends AppCompatActivity {

    final Context mainContext = this;

    private ListView albumsLV;
    public static AlbumsList aList;
    //public static final String ALBUM_NAME = "album_name";
    //public static final String ALBUM_INFO = "album_info";

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
        FloatingActionButton cancel = findViewById(R.id.cancel);

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

//        albumsLV.setOnItemClickListener(
//                (AdapterView<?> av, View view, int pos, long id) -> openAlbum(pos)
//        );

    }
}

//    private void openAlbum(int pos){
//        Bundle bundle = new Bundle();
//        bundle.putString(ALBUM_NAME, usr.albums.get(pos).getName());
//        Intent intent = new Intent(this, AlbumInfoActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }


