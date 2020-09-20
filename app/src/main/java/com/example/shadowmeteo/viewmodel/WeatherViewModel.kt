package com.example.shadowmeteo.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shadowmeteo.data.OpenWeatherProvider
import com.example.shadowmeteo.data.WeatherProvider
import com.example.shadowmeteo.model.WeatherDailyInfo
import com.example.shadowmeteo.model.WeatherInfo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherViewModel: ViewModel() {

    enum class AppError {
        MISSING_PERMISSIONS,
        PROVIDER_ERROR
    }

    val location = MutableLiveData<Location>()
    val todayWeather = MutableLiveData<WeatherInfo>()
    val nextWeekWeather = MutableLiveData<List<WeatherDailyInfo>>()
    val currentErrors = MutableLiveData<MutableSet<AppError>>()

    private val weatherProvider: WeatherProvider = OpenWeatherProvider()

    fun setCurrentLocation(currentLocation: Location) {
        this.location.postValue(currentLocation)

    }

    fun initTodayWeather() {
        location.value?.let { currentLocation ->
            weatherProvider.getCurrentWeather(currentLocation)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext = { todayWeather.postValue(it) },
                    onError = { addError(AppError.PROVIDER_ERROR) }
                )
        }
    }

    fun initWeekWeather() {
        location.value?.let { currentLocation ->
            weatherProvider.getWeekWeather(currentLocation)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext = { nextWeekWeather.postValue(it) },
                    onError = { addError(AppError.PROVIDER_ERROR) }
                )
        }
    }

    fun addError(error: AppError) {
        currentErrors.value?.add(error) ?: run {
            currentErrors.postValue(mutableSetOf(error))
        }
    }
}