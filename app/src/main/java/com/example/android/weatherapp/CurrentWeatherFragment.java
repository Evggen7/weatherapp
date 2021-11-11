package com.example.android.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.weatherapp.WeatherViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentWeatherFragment extends Fragment {

    @BindView(R.id.date)
    TextView currentWeatherDate;

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.weather_icon)
    ImageView currentWeatherIcon;

    @BindView(R.id.weather_description)
    TextView currentWeatherDescription;

    @BindView(R.id.temperature)
    TextView currentTemperature;

    @BindView(R.id.current_weather_progress_spinner)
    ProgressBar loadingSpinner;

    @BindView(R.id.weather_layout_container)
    LinearLayout weatherLayoutContainer;

    private static final String TAG = "CurrentWeatherFragment";
    private WeatherViewModel weatherViewModel;


    public static com.example.android.weatherapp.CurrentWeatherFragment newInstance() {
        return new com.example.android.weatherapp.CurrentWeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherViewModel = ViewModelProviders.of(requireActivity()).get(WeatherViewModel.class);
        setupObserversViewModel();
        weatherViewModel.fetchLocationAndWeatherData(false, getContext(), getLocationFromSharePreferences());
    }

    @NonNull
    private String getLocationFromSharePreferences() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        return sharedPrefs.getString(
                getString(R.string.location_preference_key),
                getString(R.string.settings_select_location_default)
        );
    }


    private void setupObserversViewModel() {
        weatherViewModel.baseWeatherResponseMutableLiveData.observe(getViewLifecycleOwner(), baseWeatherResponseModel -> {
            updateCurrentWeatherInfoUI(baseWeatherResponseModel.getCurrentWeatherModel());
        });
        weatherViewModel.currentLocationModelMutableLiveData.observe(getViewLifecycleOwner(), this::updateCurrentLocationUI);
        weatherViewModel.cityLocationModelMutableLiveData.observe(getViewLifecycleOwner(), this::updateCityLocationUI);
        weatherViewModel.isLoadingMutableLiveData.observe(getViewLifecycleOwner(), this::setLoadingSpinnerVisibility);
        weatherViewModel.errorLoadingMutableLiveData.observe(getViewLifecycleOwner(), isError -> {
            if(isError!=null) {
                setErrorStateVisibility(isError);
            }
        });
    }

    private void setErrorStateVisibility(boolean show) {
        weatherLayoutContainer.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void setLoadingSpinnerVisibility(boolean show) {
        loadingSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            weatherLayoutContainer.setVisibility(View.GONE);
        }
    }

    private void updateCurrentLocationUI(CurrentLocationModel currentLocationModel) {
        location.setText(getString(R.string.location_format, currentLocationModel.getCityName(), currentLocationModel.getRegionName()));
    }

    private void updateCityLocationUI(CityLocationModel cityLocationModel) {
        location.setText(cityLocationModel.getDisplayName());
    }

    private void updateCurrentWeatherInfoUI(CurrentWeatherModel currentWeatherModel) {
        currentTemperature.setText(getString(R.string.temperature_unit, currentWeatherModel.getTemperature()));
        currentWeatherDescription.setText(currentWeatherModel.getBasicWeatherModelList().get(0).getWeatherDescriptionCapitalized());
        currentWeatherDate.setText(DateUtils.getFormattedDateFromEpoch(currentWeatherModel.getCurrentTime(), getString(R.string.EEEE_MMMM_dd_pattern)));
        ImageUtils.loadImage(currentWeatherIcon,
                getString(R.string.open_weather_image_url, currentWeatherModel.getBasicWeatherModelList().get(0).getWeatherIconCode()),
                ImageUtils.getProgressDrawable(currentWeatherIcon.getContext()));
    }
}
