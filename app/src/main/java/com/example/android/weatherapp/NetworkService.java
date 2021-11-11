package com.example.android.weatherapp;

import com.example.android.weatherapp.DaggerApiComponent;
import com.example.android.weatherapp.BaseWeatherResponseModel;
import com.example.android.weatherapp.CityLocationModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class NetworkService {
    private static com.example.android.weatherapp.NetworkService instance;

    @Inject
    public OpenWeatherMapApiService weatherMapApiService;

    @Inject
    public IpApiService ipApiService;

    @Inject
    public LocationIQService locationIQService;

    private NetworkService() {
        DaggerApiComponent.create().inject(this);
    }

    public static com.example.android.weatherapp.NetworkService getInstance() {
        if (instance == null) {
            instance = new com.example.android.weatherapp.NetworkService();
        }
        return instance;
    }

    public Single<CurrentLocationModel> getCurrentLocation() {
        return ipApiService.getCurrentLocation();
    }

    public Single<BaseWeatherResponseModel> getWeatherData(String lat, String lon) {
        return weatherMapApiService.getWeatherData(lat, lon, OpenWeatherMapApiService.UNIT_METRIC, OpenWeatherMapApiService.OPEN_WEATHER_API_KEY);
    }

    public Single<List<CityLocationModel>> getCityLocation(String cityName) {
        return locationIQService.getCityLocation(LocationIQService.LOCATION_IQ_API_KEY, cityName, LocationIQService.FORMAT);
    }

}
