package com.example.cs213_android;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.view.ViewGroup;
import android.view.*;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<String> captions;
    private ArrayList<Photo> photos;

    public PhotoAdapter(Context context, ArrayList<String> captions, ArrayList<Photo> photos){
        super(context, R.layout.photo, photos);
        this.context = context;
        this.captions = captions;
        this.photos = photos;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if(currentView == null){
            gridView = inflater.inflate(R.layout.photo, null);

            TextView textView = (TextView) gridView.findViewById(R.id.caption);

            Photo photo = photos.get(position);

            Drawable drawable = new BitmapDrawable(context.getResources(), photo.getImage());
            if(drawable != null){
                drawable.setBounds(0,0, 250,250);
            }

            textView.setCompoundDrawables(null, drawable,null,null);
            textView.setText(captions.get(position));
        }else{
            gridView = (View)currentView;
        }
        return gridView;
    }

}
