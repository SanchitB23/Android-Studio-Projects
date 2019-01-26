package com.example.android.quakereport;

/**
 * Created by sanch on 04-03-2018.
 */

public class Detail {

    private String mPlace, mDate, mTime,mUrl;
    private double mMagnitude;

    public String getmUrl() {
        return mUrl;
    }

    public Detail(double magnitude, String place, String date, String time, String url) {
        mMagnitude = magnitude;
        mPlace = place;
        mDate = date;
        mTime = time;
        mUrl = url;

    }

    public String getmPlace() {
        return mPlace;
    }

    public String getmDate() {
        return mDate;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmTime() {
        return mTime;
    }

}
