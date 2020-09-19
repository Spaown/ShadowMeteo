package com.example.shadowmeteo.data

import android.location.Location
import com.example.shadowmeteo.model.WeatherInfo
import io.reactivex.rxjava3.core.Observable

interface WeatherProvider {
    fun getCurrentWeather(location: Location): Observable<WeatherInfo>
}