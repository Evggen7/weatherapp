package com.example.android.weatherapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.example.android.weatherapp.R
import com.example.android.weatherapp.MiscUtils
import com.example.android.weatherapp.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        ButterKnife.bind(this)
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        setupTabLayoutWithViewPager()
        setupSwipeRefreshListener()
        setSupportActionBar(weather_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.weather_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.more) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupSwipeRefreshListener() {
        swipe_refresh_layout.setOnRefreshListener {
            weatherViewModel.fetchLocationAndWeatherData(true, this.applicationContext, MiscUtils.getLocationFromSharePreferences(this.applicationContext))
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun setupTabLayoutWithViewPager() {
        weather_view_pager.isUserInputEnabled = false
        val tabsPagerAdapter = TabsPagerAdapter(this)
        weather_view_pager.adapter = tabsPagerAdapter
    }
}