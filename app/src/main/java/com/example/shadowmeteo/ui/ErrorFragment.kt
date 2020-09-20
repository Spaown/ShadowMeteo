package com.example.shadowmeteo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
                error_label.isClickable = false
            } else {
                view.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))
                val error = errors.first()
                error_label.text = getErrorTextFor(error)
                error_label.isClickable = true
                error_label.setOnClickListener { onClickError(error) }
            }
        })
    }

    private fun getErrorTextFor(error: WeatherViewModel.AppError): String {
        return when (error) {
            WeatherViewModel.AppError.MISSING_PERMISSIONS -> getString(R.string.you_need_to_accept_permissions)
            WeatherViewModel.AppError.PROVIDER_ERROR -> getString(R.string.service_down)
        }
    }

    private fun onClickError(error: WeatherViewModel.AppError) {
        when (error) {
            WeatherViewModel.AppError.MISSING_PERMISSIONS -> {
                openAppPermission()
            }
            else -> {}
        }
    }

    private fun openAppPermission() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", requireActivity().packageName, null)
        })
    }
}