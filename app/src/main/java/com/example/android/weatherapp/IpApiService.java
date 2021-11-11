package com.example.android.weatherapp;

import com.example.android.weatherapp.CurrentLocationModel;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface IpApiService {

    @GET("json")
    Single<CurrentLocationModel> getCurrentLocation();
}
