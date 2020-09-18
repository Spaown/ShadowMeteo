package com.example.shadowmeteo.data

import android.location.Location
import io.reactivex.rxjava3.core.Observable

interface WeatherProvider {
    fun getCurrentWeather(location: Location): Observable<Any>
}