package com.example.shadowmeteo.data

import android.location.Location
import com.example.shadowmeteo.model.WeatherDailyInfo
import com.example.shadowmeteo.model.WeatherInfo
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherProvider: WeatherProvider {

    private val service: OpenWeatherService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(OpenWeatherService::class.java)
    }

    override fun getCurrentWeather(location: Location): Observable<WeatherInfo> {
        return RxJavaBridge.toV3Observable(service.getCurrentWeather(location.latitude, location.longitude))
    }

    override fun getWeekWeather(location: Location): Observable<List<WeatherDailyInfo>> {
        return RxJavaBridge.toV3Observable(service.getWeekWeather(location.latitude, location.longitude))
            .map { oneCallInfo -> oneCallInfo.daily }
    }
}