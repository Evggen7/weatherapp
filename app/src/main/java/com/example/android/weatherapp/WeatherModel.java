package com.example.android.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {
    @SerializedName("dt")
    private long currentTime;
    @SerializedName("weather")
    private List<BasicWeatherModel> basicWeatherModelList;

    public WeatherModel(long currentTime, List<BasicWeatherModel> basicWeatherModelList) {
        this.currentTime = currentTime;
        this.basicWeatherModelList = basicWeatherModelList;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public List<BasicWeatherModel> getBasicWeatherModelList() {
        return basicWeatherModelList;
    }


}
