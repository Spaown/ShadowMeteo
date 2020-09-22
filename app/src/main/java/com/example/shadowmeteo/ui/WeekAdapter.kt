package com.example.shadowmeteo.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shadowmeteo.databinding.ViewHolderWeeklyBinding
import com.example.shadowmeteo.model.WeatherDailyInfo
import com.example.shadowmeteo.utils.WeatherUtils
import com.example.shadowmeteo.utils.extensions.setWeatherIconFromIconName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeekAdapter(private val data: List<WeatherDailyInfo>): RecyclerView.Adapter<WeekAdapter.WeekViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        return WeekViewHolder(ViewHolderWeeklyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        holder.bind(Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, position) }, data[position])
    }

    override fun getItemCount() = data.size

    class WeekViewHolder(private val binding: ViewHolderWeeklyBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(calendar: Calendar, weatherInfo: WeatherDailyInfo) {
            val temperatureString = weatherInfo.temperature?.tempInKelvin?.let { WeatherUtils.kelvinToCelsius(it).roundToInt() } ?: "--"
            binding.weeklyHolderTemperatureLabel.text = "$temperatureString°"
            binding.weeklyHolderWeatherIcon.setWeatherIconFromIconName(weatherInfo.weather?.firstOrNull()?.weatherIcon ?: "")
            binding.weeklyHolderDayLabel.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.time)
        }
    }
}