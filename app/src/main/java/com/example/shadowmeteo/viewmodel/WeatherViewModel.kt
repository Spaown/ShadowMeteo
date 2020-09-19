package com.example.shadowmeteo.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shadowmeteo.data.OpenWeatherProvider
import com.example.shadowmeteo.data.WeatherProvider
import com.example.shadowmeteo.model.WeatherInfo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherViewModel: ViewModel() {

    val weatherInfo = MutableLiveData<WeatherInfo>()

    private val weatherProvider: WeatherProvider = OpenWeatherProvider()

    fun setCurrentLocation(location: Location) {
        weatherProvider.getCurrentWeather(location)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = { weatherInfo.postValue(it) },
                onError = {}
            )
    }
}