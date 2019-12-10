package com.example.cs213_android;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AlbumInfoActivity extends AppCompatActivity {

    final Context albumContext = this;
    private GridView photosGV;
    private Album album;
    private static final int CHOOSE_PHOTO = 1;
    int destAlbum;
    PhotoAdapter adapter;

    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_info);

        int index = getIntent().getIntExtra("index", 0);
        album = PhotoAlbumActivity.aList.albums.get(index);
        Log.i("I:Album Photo Count(Startup)", album.getNumPhotos());
        final int albumIndex = index;

        Toolbar toolbar = findViewById(R.id.toolbar_ai);
        TextView textView = toolbar.findViewById(R.id.toolbar_aiTitle);
        textView.setText(album.getName() + ": (" + album.getNumPhotos() + ") photos");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        photosGV = findViewById(R.id.photo_grid);
        photosGV.setSelection(0);
        adapter = new PhotoAdapter(albumContext, album.getCaptions(), album.getPhotos());
        photosGV.setAdapter(adapter);

        FloatingActionButton add = findViewById(R.id.addPhoto);
        FloatingActionButton move = findViewById(R.id.movePhoto);
        FloatingActionButton delete = findViewById(R.id.deletePhoto);
        FloatingActionButton cancel = findViewById(R.id.cancelP);

        photosGV.setOnItemLongClickListener((AdapterView<?> av, View view, int pos, long id) -> {
            final int pos2 = pos;
            photosGV.requestFocusFromTouch();
            photosGV.setSelection(pos2);

            delete.show();
            move.show();
            cancel.show();
            add.hide();


            delete.setOnClickListener((View v)->{
                AlertDialog.Builder ab = new AlertDialog.Builder(albumContext);
                ab.setTitle("Do you want to delete this album?");

                ab.setPositiveButton("Yes", ((DialogInterface dialog, int which) -> {
                    album.getPhotos().remove(pos2);
                    AlbumsList.writeData(albumContext, PhotoAlbumActivity.aList.getAlbumList());
                    textView.setText(album.getName() + ": (" + album.getNumPhotos() + ") photos");
                    photosGV.setAdapter(adapter);
                    delete.hide();
                    move.hide();
                    cancel.hide();
                    add.show();
                }));

                ab.setNegativeButton("Cancel", ((DialogInterface dialog, int which) -> {
                    delete.hide();
                    move.hide();
                    cancel.hide();
                    add.show();
                    dialog.cancel();
                }));

                ab.show();

            });

            move.setOnClickListener((View v)->{
                AlertDialog.Builder ab = new AlertDialog.Builder(albumContext);
                ab.setTitle("Select a destination for this album");

                ab.setSingleChoiceItems(getAlbumNames(), -1, ((DialogInterface dialog, int which) -> {
                    destAlbum = which;
                }));

                ab.setPositiveButton("Move", ((DialogInterface dialog, int which) -> {
                    Photo photoToMove = album.getPhotos().get(pos2);
                    PhotoAlbumActivity.aList.getAlbumList().get(destAlbum).addPhoto(photoToMove);
                    album.getPhotos().remove(pos2);
                    AlbumsList.writeData(albumContext, PhotoAlbumActivity.aList.getAlbumList());
                    textView.setText(album.getName() + ": (" + album.getNumPhotos() + ") photos");
                    adapter = new PhotoAdapter(albumContext,album.getCaptions(), album.getPhotos());
                    photosGV.setAdapter(adapter);
                    delete.hide();
                    move.hide();
                    cancel.hide();
                    add.show();
                }));

                ab.setNegativeButton("Cancel", ((DialogInterface dialog, int which) -> {
                    delete.hide();
                    move.hide();
                    cancel.hide();
                    add.show();
                    dialog.cancel();
                }));
                ab.show();

            });

            cancel.setOnClickListener((View v)->{
                Toast returning = Toast.makeText(albumContext, "Returning to " + album.getName(), Toast.LENGTH_SHORT);
                returning.show();
                delete.hide();
                move.hide();
                cancel.hide();
                add.show();
            });
            return true;
        });


        photosGV.setOnItemClickListener((AdapterView<?> av, View view, int pos, long id) -> {
            Intent intent = new Intent(AlbumInfoActivity.this, PhotoDisplayActivity.class);
            intent.putExtra("album_index", albumIndex);
            intent.putExtra("photo_index", pos);
            startActivity(intent);

        });

            add.setOnClickListener((View v)-> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, CHOOSE_PHOTO);
        });

    }

    public CharSequence[] getAlbumNames(){
        CharSequence[] aNames = new CharSequence[PhotoAlbumActivity.aList.getAlbumList().size()];
        for(int i = 0; i < PhotoAlbumActivity.aList.getAlbumList().size(); i++){
            Log.i("Album",PhotoAlbumActivity.aList.getAlbumList().get(0).getName());
            aNames[i] = PhotoAlbumActivity.aList.getAlbumList().get(i).getName();
        }
        return aNames;
    }

    public String photoFileName(Uri picture){
        String caption = "N/A";
        String[] col = {MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver cr = getApplicationContext().getContentResolver();
        Cursor c = cr.query(picture, col, null, null, null);

        if(c != null){
            try{
                if(c.moveToFirst()){
                    caption = c.getString(0);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return caption;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent selectedImageIntent){
        Log.i("onActivityResult", "called");

        super.onActivityResult(requestCode, resultCode, selectedImageIntent);

        if(resultCode == RESULT_OK){
            try {
                Drawable gridPic;
                Uri selectedImage = selectedImageIntent.getData();
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                gridPic = Drawable.createFromStream(inputStream, selectedImage.toString());
                gridPic.setBounds(0,0, 50,50);

                File file = new File(selectedImage.getPath());
                String caption = photoFileName(selectedImage);

                BitmapDrawable bd = (BitmapDrawable) gridPic;
                Bitmap picBitmap = bd.getBitmap();

                Photo newPhoto = new Photo(picBitmap, album, caption);
                album.addPhoto(newPhoto);
                Log.i("Album Photo Count(Post-Selection)", album.getNumPhotos());

                AlbumsList.writeData(this, PhotoAlbumActivity.aList.getAlbumList());

                adapter = new PhotoAdapter(this, album.getCaptions(), album.getPhotos());
                photosGV.setAdapter(adapter);

                TextView textView = findViewById(R.id.toolbar_aiTitle);
                textView.setText(album.getName() + ": (" + album.getNumPhotos() + ") photos");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
