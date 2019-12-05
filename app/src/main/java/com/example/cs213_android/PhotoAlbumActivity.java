package com.example.cs213_android;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.TextView;

import android.os.Bundle;

//import android.content.Intent;

import java.util.ArrayList;

public class PhotoAlbumActivity extends AppCompatActivity {

    private ListView albumsLV;
    public static AlbumsList aList;
    //public static ArrayList<Album> albums;
    //public static final String ALBUM_NAME = "album_name";
    //public static final String ALBUM_INFO = "album_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aList = new AlbumsList();
        aList.albums = AlbumsList.readData(this);


        if(aList == null ){
            Log.i("Albums List Object is", "null");
            aList = new AlbumsList();
            aList.albums.add(new Album("Album1"));
            aList.albums.add(new Album("Album2"));
            AlbumsList.writeData(this, aList.albums);
            Log.i("AAlbum1 Name ", aList.albums.get(0).getName());
            Log.i("AAlbum2 Name ", aList.albums.get(1).getName());
        }else{
            Log.i("Albums list Object is", "not null");
        }

        if(aList.albums == null ){
            Log.i("Albums list is", "null");
            aList.albums = new ArrayList<Album>();
            aList.albums.add(new Album("Album1"));
            aList.albums.add(new Album("Album2"));
            AlbumsList.writeData(this, aList.albums);
            Log.i("AAlbum1 Name ", aList.albums.get(0).getName());
            Log.i("AAlbum2 Name ", aList.albums.get(1).getName());
        }else{
            Log.i("Albums list is", "not null");
        }

        if(aList.albums.isEmpty()){
            Log.i("Albums list is", "empty");
            aList.albums.add(new Album("Album1"));
            aList.albums.add(new Album("Album2"));
            AlbumsList.writeData(this, aList.albums);
            Log.i("EAlbum1 Name ", aList.albums.get(0).getName());
            Log.i("EAlbum2 Name ", aList.albums.get(1).getName());
        }else{
            Log.i("Albums list is", "not empty");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_list);

        albumsLV = findViewById(R.id.albums_list);
        albumsLV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        albumsLV.setSelection(0);

        ArrayAdapter<Album> adapter = new ArrayAdapter<Album>(this, R.layout.album, aList.albums);
        albumsLV.setAdapter(adapter);

        //albumsLV.setOnItemLongClickListener();

//        albumsLV.setOnItemClickListener(
//                (p,v,pos,id)-> openAlbum(pos)
//        );


    }
//    private void openAlbum(int pos){
//        Bundle bundle = new Bundle();
////        bundle.putString(ALBUM_NAME, usr.albums.get(pos).getName());
//        Intent intent = new Intent(this, AlbumInfoActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//
//    private void albumOptions(int pos){
//        Bundle bundle = new Bundle();
////        bundle.putString(ALBUM_NAME, usr.albums.get(pos).getName());
//        Intent intent = new Intent(this, AlbumInfoActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//

//    public int getAlbumByName(String albumName){
//        for(int i = 0; i < usr.albums.size(); i++){
//            if(usr.albums.get(i).getName().equalsIgnoreCase(albumName)){
//                return i;
//            }
//        }
//        return -1;
//    }
}
