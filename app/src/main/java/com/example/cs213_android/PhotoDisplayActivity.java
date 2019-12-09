package com.example.cs213_android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PhotoDisplayActivity extends AppCompatActivity {
    final Context photoContext = this;
    private ImageView photoIV;
    private ListView tagsLV;
    Album album;
    Photo currPhoto;
    ArrayAdapter<Tag> tagsAdapter;
    String selectedTag;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_display);

        FloatingActionButton prev = findViewById(R.id.left);
        FloatingActionButton next = findViewById(R.id.right);

        FloatingActionButton add = findViewById(R.id.addTag);
        FloatingActionButton edit = findViewById(R.id.editTag);
        FloatingActionButton delete = findViewById(R.id.deleteTag);
        FloatingActionButton cancel = findViewById(R.id.cancelT);

        final int albumI = getIntent().getIntExtra("album_index", 0);
        final int photoI = getIntent().getIntExtra("photo_index", 0);

        album = PhotoAlbumActivity.aList.getAlbumList().get(albumI);
        currPhoto = PhotoAlbumActivity.aList.getAlbumList().get(albumI).getPhotos().get(photoI);

        if(photoI == 0){
            prev.hide();
        }else{
            prev.show();
        }

        if(photoI == album.getPhotos().size()-1 ){
            next.hide();
        }else{
            next.show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar3);
        TextView textView = toolbar.findViewById(R.id.toolbar3_Title);
        textView.setText(currPhoto.getCaption());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.full_image);
        imageView.setImageBitmap(currPhoto.getImage());

        tagsLV = findViewById(R.id.tags_list);
        tagsLV.setSelection(0);
        tagsAdapter = new ArrayAdapter<Tag>(photoContext, R.layout.tag, currPhoto.getTags());
        if(currPhoto.getTags().isEmpty()){
            Log.i("Photo: " + currPhoto.getCaption(),"tags are empty");
        }
        tagsLV.setAdapter(tagsAdapter);

        add.setOnClickListener((View v)->{
            AlertDialog.Builder ab = new AlertDialog.Builder(photoContext);
            ab.setTitle("Add a tag");

            LinearLayout ll = new LinearLayout(this);

            Spinner spinner = new Spinner(this);
            spinner.setPrompt("Choose a Tag type from the menu and enter a value");
            ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this, R.array.tag_types, android.R.layout.simple_spinner_item);
            sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(sAdapter);

            final EditText inputValue = new EditText(photoContext);
            inputValue.setInputType(InputType.TYPE_CLASS_TEXT);

            ll.addView(spinner, 0);
            ll.addView(inputValue, 1);

            ab.setView(ll);

            ab.setPositiveButton("Apply", ((DialogInterface dialog, int which) -> {
                String newType = spinner.getSelectedItem().toString();
                String newValue = inputValue.getText().toString();
                if(currPhoto.duplicateTag(new Tag(newType, newValue))){
                    AlertDialog.Builder dupe = new AlertDialog.Builder(photoContext);
                    dupe.setTitle("Duplicate Tag");
                    dupe.setMessage("An tag with that type-value pair exists for this photo");
                    dupe.setPositiveButton("OK", ((DialogInterface di, int d) -> {
                        di.dismiss();
                        delete.hide();
                        edit.hide();
                        cancel.hide();
                        add.show();
                        return;
                    }));
                    dupe.show();
                }else if(newValue.trim().isEmpty()){
                    AlertDialog.Builder dupe = new AlertDialog.Builder(photoContext);
                    dupe.setTitle("Invalid Input");
                    dupe.setMessage("New tag value must not be blank");
                    dupe.setPositiveButton("OK", ((DialogInterface di, int d) -> {
                        di.dismiss();
                        delete.hide();
                        edit.hide();
                        cancel.hide();
                        add.show();
                        return;
                    }));
                    dupe.show();
                }else{
                    Tag tag = new Tag(newType, newValue);
                    currPhoto.addTag(tag);
                    AlbumsList.writeData(photoContext, PhotoAlbumActivity.aList.getAlbumList());
                    tagsLV.setAdapter(tagsAdapter);
                    delete.hide();
                    edit.hide();
                    cancel.hide();
                    add.show();
                }
            }));

            ab.setNegativeButton("Cancel", ((DialogInterface dialog, int which) -> {
                dialog.cancel();
            }));
            ab.show();
        });

        tagsLV.setOnItemClickListener((AdapterView<?> av, View view, int pos, long id) -> {
            final int pos2 = pos;
            tagsLV.requestFocusFromTouch();
            tagsLV.setSelection(pos2);

            delete.show();
            edit.show();
            cancel.show();
            add.hide();

            edit.setOnClickListener((View v)->{
                AlertDialog.Builder ab = new AlertDialog.Builder(photoContext);
                ab.setTitle("Edit tag");

                LinearLayout ll = new LinearLayout(this);

                Spinner spinner = new Spinner(this);
                spinner.setPrompt("Choose a Tag type from the menu and enter a value");
                ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this, R.array.tag_types, android.R.layout.simple_spinner_item);
                sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(sAdapter);

                final EditText inputValue = new EditText(photoContext);
                inputValue.setInputType(InputType.TYPE_CLASS_TEXT);

                ll.addView(spinner, 0);
                ll.addView(inputValue, 1);

                ab.setView(ll);

                ab.setPositiveButton("Apply", ((DialogInterface dialog, int which) -> {
                    String newType = spinner.getSelectedItem().toString();
                    String newValue = inputValue.getText().toString();
                    if(currPhoto.duplicateTag(new Tag(newType, newValue))){
                        AlertDialog.Builder dupe = new AlertDialog.Builder(photoContext);
                        dupe.setTitle("Duplicate Tag");
                        dupe.setMessage("An tag with that type-value pair exists for this photo");
                        dupe.setPositiveButton("OK", ((DialogInterface di, int d) -> {
                            di.dismiss();
                            delete.hide();
                            edit.hide();
                            cancel.hide();
                            add.show();
                            return;
                        }));
                        dupe.show();
                    }else if(newValue.trim().isEmpty()){
                        AlertDialog.Builder dupe = new AlertDialog.Builder(photoContext);
                        dupe.setTitle("Invalid Input");
                        dupe.setMessage("New tag value must not be blank");
                        dupe.setPositiveButton("OK", ((DialogInterface di, int d) -> {
                            di.dismiss();
                            delete.hide();
                            edit.hide();
                            cancel.hide();
                            add.show();
                            return;
                        }));
                        dupe.show();
                    }else{
                        currPhoto.getTags().get(pos2).setType(newType);
                        currPhoto.getTags().get(pos2).setValue(newValue.trim());
                        AlbumsList.writeData(photoContext, PhotoAlbumActivity.aList.getAlbumList());
                        tagsLV.setAdapter(tagsAdapter);
                        delete.hide();
                        edit.hide();
                        cancel.hide();
                        add.show();
                    }
                }));

                ab.setNegativeButton("Cancel", ((DialogInterface dialog, int which) -> {
                    dialog.cancel();
                }));
                ab.show();
            });

            delete.setOnClickListener((View v)-> {
                AlertDialog.Builder ab = new AlertDialog.Builder(photoContext);
                ab.setTitle("Do you want to delete this tag");

                ab.setPositiveButton("Yes", ((DialogInterface dialog, int which) -> {
                    currPhoto.getTags().remove(pos2);
                    AlbumsList.writeData(photoContext, PhotoAlbumActivity.aList.getAlbumList());
                    tagsLV.setAdapter(tagsAdapter);
                    delete.hide();
                    edit.hide();
                    cancel.hide();
                    add.show();
                }));

                ab.setNegativeButton("Cancel", ((DialogInterface dialog, int which) -> {
                    delete.hide();
                    edit.hide();
                    cancel.hide();
                    add.show();
                }));
                ab.show();
            });

            cancel.setOnClickListener((View v) -> {
                Toast returning = Toast.makeText(photoContext, "Returning to '" + currPhoto.getCaption() + "'", Toast.LENGTH_SHORT);
                returning.show();
                delete.hide();
                edit.hide();
                cancel.hide();
                add.show();
            });
        });

        prev.setOnClickListener((View v)-> {
            Intent intent = new Intent(PhotoDisplayActivity.this, PhotoDisplayActivity.class);
            intent.putExtra("album_index", albumI);
            int nextIndex = photoI - 1;
            intent.putExtra("photo_index", nextIndex);
            startActivity(intent);
        });

        next.setOnClickListener((View v)-> {
            Intent intent = new Intent(PhotoDisplayActivity.this, PhotoDisplayActivity.class);
            intent.putExtra("album_index", albumI);
            int nextIndex = photoI + 1;
            intent.putExtra("photo_index", nextIndex);
            startActivity(intent);
        });
    }
}
