package com.example.android.weatherapp;

import com.example.android.weatherapp.CityLocationModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationIQService {

    String LOCATION_IQ_API_KEY = "9a43a7b511e45a";
    String FORMAT = "json";

    @GET("v1/search.php")
    Single<List<CityLocationModel>> getCityLocation(@Query(value = "key", encoded = true) String apiKey,
                                                    @Query(value = "q", encoded = true) String cityName,
                                                    @Query(value = "format", encoded = true) String format);
}
