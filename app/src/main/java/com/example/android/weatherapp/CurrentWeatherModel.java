package com.example.android.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherModel extends WeatherModel {

    @SerializedName("temp")
    private double temperature;


    public CurrentWeatherModel(long currentTime, double temperature, List<BasicWeatherModel> basicWeatherModelList) {
        super(currentTime, basicWeatherModelList);
        this.temperature = temperature;
    }



    public double getTemperature() {
        return temperature;
    }

}
