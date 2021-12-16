package com.sergiop.aparcamientos.ui.activitys.home

import android.location.Location
import androidx.lifecycle.viewModelScope
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.extensions.ViewModelLocation
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.notifications.AppNotificacionManager
import com.sergiop.aparcamientos.notifications.NotificationData
import com.sergiop.aparcamientos.ui.activitys.home.screens.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.ServiceLocationProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(private val repository : DataRepository,
                                                private val notificationManagerLocal : AppNotificacionManager,
                                                requestLocationHome: SmartLocation,
                                                providerLocationHome: ServiceLocationProvider): ViewModelLocation(requestLocationHome, providerLocationHome) {

    private fun garageNear(actualLocation : Location?) {
        viewModelScope.launch {
            if (actualLocation != null) {
                val result : Result<Garaje?> = repository.getGarageNear(actualLocation, 1)
                if (result.isSuccess) {
                    val garaje = result.getOrNull()
                    notificationManagerLocal.showLocalNotification(
                        NotificationData(
                        "Garage: ${garaje?.direccion}",
                        "Este es el garage MAS CERCANO",
                        channelId = "Aparcamientos")
                    )
                    //_state.value = HomeState(error= false, nearGarage = result.getOrNull())
                } else {
                    //_state.value = HomeState(error = true, messageError = result.exceptionOrNull()?.message?:"Error retrieve garage near")
                }
            } else {
                //_state.value = HomeState(error = true, messageError = "Error retrieve current location")
            }
        }
        
    }

    override fun onLocationUpdated(location: Location?) {
        super.onLocationUpdated(location)

        location?.let { garageNear(location) }
    }

    override fun onCancelLocation() {
        super.onCancelLocation()
    }

}