package com.example.android.weatherapp;

import com.example.android.weatherapp.WeatherViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(NetworkService networkService);

    void inject(WeatherViewModel weatherViewModel);
}
