package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class DetailsAdapter extends ArrayAdapter<Detail>{

    DetailsAdapter(Activity context, ArrayList<Detail> details) {
        super(context,0,details);
    }

    private static final String PLACE_SEPARATOR = " of ";
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        Detail currentDetail = getItem(position);


        String getPlace = currentDetail.getmPlace();
        String placeName;
        String placeProximityText;

        if (getPlace.contains(PLACE_SEPARATOR)){
            String[] parts = getPlace.split(PLACE_SEPARATOR);
            placeProximityText = parts[0] + PLACE_SEPARATOR;
            placeName = parts[1];
        }
        else {
            placeProximityText = getContext().getString(R.string.near_the);
            placeName = getPlace;
        }

        Double magnitude = currentDetail.getmMagnitude();
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String magnitudeText = decimalFormat.format(magnitude);


        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text);
        magnitudeTextView.setText(magnitudeText);

        TextView placeText = (TextView) listItemView.findViewById(R.id.place_text);
        placeText.setText(placeName);

        TextView placeProximity = (TextView) listItemView.findViewById(R.id.place_proximity_text);
        placeProximity.setText(placeProximityText);

        TextView dateText = (TextView) listItemView.findViewById(R.id.date_text);
        dateText.setText(currentDetail.getmDate());

        TextView timeText = (TextView) listItemView.findViewById(R.id.time_text);
        timeText.setText(currentDetail.getmTime());

        GradientDrawable magnitudeCircle = (GradientDrawable)magnitudeTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(magnitude);
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;
    }

    int getMagnitudeColor(double magnitude){

        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


}
