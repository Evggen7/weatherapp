package com.example.android.weatherapp;

import android.content.Context;

import com.example.android.weatherapp.R;
import com.example.android.weatherapp.NetworkService;
import com.example.android.weatherapp.DaggerApiComponent;
import com.example.android.weatherapp.BaseWeatherResponseModel;
import com.example.android.weatherapp.CityLocationModel;
import com.example.android.weatherapp.CurrentLocationModel;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {

    public MutableLiveData<BaseWeatherResponseModel> baseWeatherResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorLoadingMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<CurrentLocationModel> currentLocationModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<CityLocationModel> cityLocationModelMutableLiveData = new MutableLiveData<>();

    @Inject
    public NetworkService networkService;

    private CompositeDisposable disposable = new CompositeDisposable();

    public WeatherViewModel() {
        super();
        DaggerApiComponent.create().inject(this);
    }

    public void fetchLocationAndWeatherData(Boolean isRefreshing, Context context, String location) {
        if (!isRefreshing) {
            isLoadingMutableLiveData.setValue(true);
        }

        if (location.equals(context.getString(R.string.settings_location_current_value))) {
            fetchCurrentLocationWeatherData();
        } else {
            fetchCityLocationWeatherData(location);
        }
    }

    private void fetchCityLocationWeatherData(String cityName) {
        disposable.add(
                networkService.getCityLocation(cityName)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CityLocationModel>>() {
                            @Override
                            public void onSuccess(List<CityLocationModel> cityLocationModelList) {
                                cityLocationModelMutableLiveData.setValue(cityLocationModelList.get(0));
                                if (cityLocationModelMutableLiveData.getValue() != null) {
                                    fetchWeatherDataFromService(String.valueOf(cityLocationModelMutableLiveData.getValue().getCityLatitude()), String.valueOf(cityLocationModelMutableLiveData.getValue().getCityLongitude()));
                                } else {
                                    errorLoadingMutableLiveData.setValue(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorLoadingMutableLiveData.setValue(true);
                                isLoadingMutableLiveData.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void fetchCurrentLocationWeatherData() {
        disposable.add(
                networkService.getCurrentLocation()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CurrentLocationModel>() {
                            @Override
                            public void onSuccess(CurrentLocationModel currentLocationModel) {
                                currentLocationModelMutableLiveData.setValue(currentLocationModel);
                                if (currentLocationModelMutableLiveData.getValue() != null) {
                                    fetchWeatherDataFromService(String.valueOf(currentLocationModelMutableLiveData.getValue().getCurrentLatitude()), String.valueOf(currentLocationModelMutableLiveData.getValue().getCurrentLongitude()));
                                } else {
                                    errorLoadingMutableLiveData.setValue(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorLoadingMutableLiveData.setValue(true);
                                isLoadingMutableLiveData.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void fetchWeatherDataFromService(String lat, String lon) {
        disposable.add(
                networkService.getWeatherData(lat, lon)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<BaseWeatherResponseModel>() {
                            @Override
                            public void onSuccess(BaseWeatherResponseModel baseWeatherResponseModel) {
                                baseWeatherResponseMutableLiveData.setValue(baseWeatherResponseModel);
                                errorLoadingMutableLiveData.setValue(false);
                                isLoadingMutableLiveData.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorLoadingMutableLiveData.setValue(true);
                                isLoadingMutableLiveData.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
