package com.example.shadowmeteo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shadowmeteo.R
import com.example.shadowmeteo.model.WeatherInfo
import com.example.shadowmeteo.utils.WeatherUtils
import com.example.shadowmeteo.utils.extensions.setWeatherIconFromIconName
import com.example.shadowmeteo.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_today.*
import kotlin.math.roundToInt

class TodayFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        viewModel.weatherInfo.observe(viewLifecycleOwner, Observer { weatherInfo ->
            weatherInfo?.let { bindWeatherInfo(it) }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindWeatherInfo(weatherInfo: WeatherInfo) {
        today_loading.visibility = View.GONE

        weatherInfo.cityName?.let { today_city_label.text = it }
        weatherInfo.weather?.firstOrNull()?.weatherIcon?.let { icon ->
            today_weather_icon.setWeatherIconFromIconName(icon)
        }
        weatherInfo.weatherData?.tempInKelvin?.let { today_temperature_label.text = "${WeatherUtils.kelvinToCelsius(it).roundToInt()}°" }
        weatherInfo.weatherData?.humidityPercent?.let { today_humidity_label.text = it.roundToInt().toString() }
    }
}