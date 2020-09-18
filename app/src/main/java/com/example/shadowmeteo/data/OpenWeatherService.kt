package com.example.shadowmeteo.data

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenWeatherService {

    companion object {
        const val API_KEY = "75eb2975e6fea203853fc931905d401d"
        const val API_VERSION = 2.5
    }

    @GET("data/$API_VERSION/weather?lat={lat}&lon={lon}&appid=$API_KEY")
    fun getCurrentWeather(@Path("lat") lat: Double, @Path("lng") lng: Double): Observable<Any>
}