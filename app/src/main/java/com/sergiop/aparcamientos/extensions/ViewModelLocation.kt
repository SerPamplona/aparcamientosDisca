package com.sergiop.aparcamientos.extensions

import android.location.Location
import androidx.lifecycle.ViewModel
import com.sergiop.aparcamientos.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.ServiceLocationProvider
import android.R
import java.util.*
import kotlin.concurrent.schedule


abstract class ViewModelLocation(val requestLocation: SmartLocation,
                                 val providerLocation: ServiceLocationProvider) : ViewModel(), OnLocationUpdatedListener {

    var lastLocation : Location? = null
    var watchLocation : Boolean = false

    fun startLocation() {

        requestLocation .location(providerLocation)
                        .oneFix()
                        .start(this)

        Timer().schedule(30000) {
            if (lastLocation == null) {
                onCancelLocation()
            }
        }
    }

    fun startWatchLocation() {
        watchLocation = true
        requestLocation .location(providerLocation)
                        .start(this)

        Timer().schedule(60000) {
            if (lastLocation == null) {
                onCancelLocation()
            }
        }
    }

    fun stopWatchLocation () {
        requestLocation.location().stop()
    }

    open fun onCancelLocation () {
    }

    override fun onLocationUpdated (location: Location?) {
        if (! watchLocation) {
            requestLocation.location().stop()
        }
        lastLocation = location
    }
}