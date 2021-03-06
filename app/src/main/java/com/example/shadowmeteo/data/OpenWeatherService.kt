package com.example.shadowmeteo.data

import com.example.shadowmeteo.model.WeatherInfo
import com.example.shadowmeteo.model.WeatherOneCallInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    companion object {
        const val API_KEY = "75eb2975e6fea203853fc931905d401d"
        const val API_VERSION = 2.5
    }

    @GET("data/$API_VERSION/weather?appid=$API_KEY")
    fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lng: Double): Observable<WeatherInfo>

    @GET("data/$API_VERSION/onecall?appid=$API_KEY&exclude=current, minutely, hourly")
    fun getWeekWeather(@Query("lat") lat: Double, @Query("lon") lng: Double): Observable<WeatherOneCallInfo>
}