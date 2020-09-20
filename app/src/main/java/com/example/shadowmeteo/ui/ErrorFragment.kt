package com.example.shadowmeteo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shadowmeteo.R
import com.example.shadowmeteo.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_error.*

class ErrorFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java).currentErrors.observe(viewLifecycleOwner, Observer { errors ->
            if (errors.isNullOrEmpty()) {
                view.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
            } else {
                view.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))
                error_label.text = getErrorTextFor(errors.first())
            }
        })
    }

    private fun getErrorTextFor(error: WeatherViewModel.AppError): String {
        return when (error) {
            WeatherViewModel.AppError.MISSING_PERMISSIONS -> getString(R.string.you_need_to_accept_permissions)
            WeatherViewModel.AppError.PROVIDER_ERROR -> getString(R.string.service_down)
        }
    }
}