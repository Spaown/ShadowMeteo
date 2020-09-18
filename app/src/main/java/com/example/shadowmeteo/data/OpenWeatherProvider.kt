package com.example.shadowmeteo.data

import android.location.Location
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit

class OpenWeatherProvider: WeatherProvider {

    private val service: OpenWeatherService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .build()

        service = retrofit.create(OpenWeatherService::class.java)
    }

    override fun getCurrentWeather(location: Location): Observable<Any> {
        return service.getCurrentWeather(location.latitude, location.longitude)
    }
}