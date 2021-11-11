package com.example.android.weatherapp;

import com.google.gson.annotations.SerializedName;

public class BaseWeatherResponseModel {
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;
    @SerializedName("current")
    private CurrentWeatherModel currentWeatherModel;


    public BaseWeatherResponseModel(float latitude, float longitude, CurrentWeatherModel currentWeatherModel) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.currentWeatherModel = currentWeatherModel;

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public CurrentWeatherModel getCurrentWeatherModel() {
        return currentWeatherModel;
    }


}
