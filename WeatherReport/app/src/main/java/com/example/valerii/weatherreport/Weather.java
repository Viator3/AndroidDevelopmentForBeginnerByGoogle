package com.example.valerii.weatherreport;

/**
 * Created by Valerii on 12.12.2017.
 */

class Weather {

    private String mTemperature;
    private int mImageResourceId;
    private String mLocation;
    private String mTime;

    public Weather(String temperature, int imageResourceId, String location, String time) {
        mTemperature = temperature;
        mImageResourceId = imageResourceId;
        mLocation = location;
        mTime = time;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public int getImageResourceId() {

        return mImageResourceId;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getTime() {
        return mTime;
    }
}

