package com.example.android.weatherapp;

import com.example.android.weatherapp.BaseWeatherResponseModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApiService {

    String OPEN_WEATHER_API_KEY = "2961a449004edfb441f441c40fa07770";
    String UNIT_METRIC = "metric";

    @GET("data/2.5/onecall")
    Single<BaseWeatherResponseModel> getWeatherData(@Query(value = "lat", encoded = true) String lat,
                                                    @Query(value = "lon", encoded = true) String lon,
                                                    @Query(value = "units", encoded = true) String units,
                                                    @Query(value = "APPID", encoded = true) String apiKey);

}
