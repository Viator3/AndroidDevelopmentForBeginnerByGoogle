package com.example.valerii.miwok;

/**
 * Created by Valerii on 21.11.2017.
 */

public class Word {

    private String mDefaultTranslation;
    private String mEnglishTranslation;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private int mAudioResourceId;

    public Word (String englishTranslation, String defaultTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mEnglishTranslation = englishTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public Word (String englishTranslation, String defaultTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mEnglishTranslation = englishTranslation;
        mAudioResourceId = audioResourceId;
    }

    public String getDefaultTranslation() {

        return mDefaultTranslation;
    }

    public String getEnglishTranslation(){

        return mEnglishTranslation;
    }

    public int getImageResourceId() {

        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }


}
