package com.example.shadowmeteo.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.setWeatherIconFromIconName(iconName: String) {
    Glide.with(this).load("https://openweathermap.org/img/wn/$iconName@4x.png").into(this)
}