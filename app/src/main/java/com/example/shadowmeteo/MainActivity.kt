package com.example.shadowmeteo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shadowmeteo.viewmodel.WeatherViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity: AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions.find { it == Manifest.permission.ACCESS_FINE_LOCATION } != null) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setCurrentLocation()
            } else {
                viewModel.addError(WeatherViewModel.AppError.MISSING_PERMISSIONS)
            }
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun setCurrentLocation() {
        requestLastKnownLocation()
            .subscribeOn(Schedulers.io())
            .subscribeBy(onSuccess = { viewModel.setCurrentLocation(it) })
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun requestLastKnownLocation(): Single<Location> {
        return Single.create { emitter ->
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val providers = locationManager.getProviders(true)

            providers.mapNotNull { locationManager.getLastKnownLocation(it) }.lastOrNull()?.let {
                emitter.onSuccess(it)
            } ?: run {
                emitter.onError(Throwable("requestLastKnownLocation: Can't find last location"))
            }
        }
    }
}