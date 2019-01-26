package com.udacitytuts.miwok2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.miwok.R;

import java.util.List;

/**
 * Created by sanch on 11-02-2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    int mColorResourceID;
    public WordAdapter(@NonNull Context context, @NonNull List<Word> objects, @NonNull int colorResourceID) {
        super(context, 0, objects);
        mColorResourceID = colorResourceID;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView ==null) listItemView = LayoutInflater.from(
                getContext()).inflate(R.layout.list_item, parent,false);
        Word currentWord = getItem(position);                                                       //Get word located at this position

        View textContainer = (View) listItemView.findViewById(R.id.layout);
        int color = ContextCompat.getColor(getContext(),mColorResourceID);
        textContainer.setBackgroundColor(color);

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);        //Finds Miwok word
        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView defaultTestView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTestView.setText(currentWord.getmDefaultTranslation());

        ImageView displayImage = (ImageView) listItemView.findViewById(R.id.image);
        if (currentWord.hasImage()==true){
            displayImage.setImageResource(currentWord.getImageResourceID());
            displayImage.setVisibility(View.VISIBLE);
        }
        else displayImage.setVisibility(View.GONE);

        return listItemView;

    }
}
