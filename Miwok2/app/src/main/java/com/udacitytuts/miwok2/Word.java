package com.udacitytuts.miwok2;

/**
 * Created by sanch on 11-02-2018.
 */

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private final int NO_ID = -1;
    private int mImageResourceID =NO_ID;
    private int mAudioResourceID = NO_ID;


    public Word(String defaultTranslation, String miwokTranslation, int imageResourceID, int audioResourceID){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceID = imageResourceID;
        mAudioResourceID = audioResourceID;
    }

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceID){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceID = audioResourceID;

    }

    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }
    public String getmDefaultTranslation(){
        return mDefaultTranslation;
    }
    public int getImageResourceID(){
        return mImageResourceID;
    }
    public int getAudioResourceID(){
        return mAudioResourceID;
    }
    public boolean hasImage(){
        return mImageResourceID !=NO_ID;
    }
}
