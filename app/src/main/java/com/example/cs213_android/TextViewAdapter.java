package com.example.cs213_android;


import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.*;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class TextViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> captions;
    private ArrayList<Photo> photos;

    public TextViewAdapter(Context context, ArrayList<String> captions, ArrayList<Photo> photos){
        this.context = context;
        this.captions = captions;
        this.photos = photos;

    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if(currentView == null){
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.photo, null);

            TextView textView = (TextView) gridView.findViewById(R.id.caption);

            Photo photo = photos.get(position);
            Drawable drawable = photo.getImage();

            textView.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
            textView.setText(captions.get(position));

        }else{
            gridView = (View)currentView;
        }
        return gridView;
    }

    @Override
    public int getCount(){
        return captions.size();
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }
}
