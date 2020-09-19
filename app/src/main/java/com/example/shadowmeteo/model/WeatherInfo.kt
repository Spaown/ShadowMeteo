package com.example.shadowmeteo.model

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("name")
    val cityName: String?,

    @SerializedName("weather")
    val weather: List<Weather>?,

    @SerializedName("main")
    val weatherData: WeatherData?
)

data class Weather(
    @SerializedName("main")
    val weatherName: String?,

    @SerializedName("icon")
    val weatherIcon: String?
)

data class WeatherData(
    @SerializedName("temp")
    val tempInKelvin: Double?,

    @SerializedName("humidity")
    val humidityPercent: Double?
)