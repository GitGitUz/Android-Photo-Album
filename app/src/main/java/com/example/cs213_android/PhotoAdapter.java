package com.example.cs213_android;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.view.*;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<String> captions;
    private ArrayList<Photo> photos;

    public PhotoAdapter(Context context, ArrayList<String> captions, ArrayList<Photo> photos){
        super(context, R.layout.photo, photos);
        Log.i("PhotoAdapter Constructor", "called");
        this.context = context;
        this.captions = captions;
        this.photos = photos;
        for(Photo p : photos){
            if(p.getImage() == null){
                Log.i("C:Picture Bitmap is", "null");
            }else{
                Log.i("C:Picture Bitmap is", "not null");

            }
        }

    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent){
        Log.i("PhotoAdapter getView()", "called");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if(currentView == null){
            Log.i("CurrentView is: ", "null");
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.photo, null);

            TextView textView = (TextView) gridView.findViewById(R.id.caption);

            Photo photo = photos.get(position);
            for(Photo p : photos){
                if(p.getImage() == null){
                    Log.i("V:Picture Bitmap is", "null");
                }else{
                    Log.i("V:Picture Bitmap is", "not null");

                }
            }
            Drawable drawable = new BitmapDrawable(context.getResources(), photo.getImage());
            if(drawable != null){
                Log.i("Drawable is", "not null");
                drawable.setBounds(0,0, 250,250);
            }else{
                Log.i("Drawable is", "null");
            }

            textView.setCompoundDrawables(null, drawable,null,null);
            textView.setText(captions.get(position));
            Log.i("Caption", (String)textView.getText());

        }else{
            gridView = (View)currentView;
        }
        return gridView;
    }

}
