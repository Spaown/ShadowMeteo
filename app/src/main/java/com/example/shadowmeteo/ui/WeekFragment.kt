package com.example.shadowmeteo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shadowmeteo.R
import com.example.shadowmeteo.model.WeatherDailyInfo
import com.example.shadowmeteo.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_week.*

class WeekFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        viewModel.location.observe(viewLifecycleOwner, Observer { location ->
            if (location != null && viewModel.nextWeekWeather.value == null) {
                viewModel.initWeekWeather()
            }
        })
        viewModel.nextWeekWeather.observe(viewLifecycleOwner, Observer { weatherList ->
            weatherList?.let { bindWeatherList(it) }
        })
    }

    private fun bindWeatherList(weatherList: List<WeatherDailyInfo>) {
        weekly_loading.visibility = View.GONE
        weekly_list.layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.HORIZONTAL, false)
        weekly_list.adapter = WeekAdapter(weatherList)
    }
}