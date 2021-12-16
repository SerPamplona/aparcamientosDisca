package com.sergiop.aparcamientos.ui.activitys.home.screens.registerParking

import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.extensions.ViewModelLocation
import com.sergiop.aparcamientos.model.Persona
import dagger.hilt.android.lifecycle.HiltViewModel
import io.nlopez.smartlocation.OnLocationUpdatedListener
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.ServiceLocationProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository : DataRepository,
                                            requestLocationHome: SmartLocation,
                                            providerLocationHome: ServiceLocationProvider
): ViewModelLocation(requestLocationHome, providerLocationHome), OnLocationUpdatedListener {

    private val _state = mutableStateOf(RegisterParkingState())
    val registerParkingState: State<RegisterParkingState> = _state
    var currentLocationObject = MutableStateFlow(Location(""))

    @Inject
    lateinit var userLogged : Persona

    //se crea una lista observable
    var observerRegisterGarage = MutableStateFlow<Result<String?>>(Result.failure(Throwable()))

    //se crean las variable, para apartir de ellas, darle valor desde la vista
    var largo : Float = 0.0f
    var ancho : Float = 0.0f
    var moto : Boolean = false
    var direccion : String  = ""

    fun registerParking () {

        val garage = Garaje(moto, largo, ancho, currentLocationObject.value.latitude, currentLocationObject.value.longitude, propietario = userLogged, direccion = direccion)

        viewModelScope.launch {
            val valueResult = async {repository.registerParking(garage) }
            observerRegisterGarage.value = valueResult.await()
        }
    }

    fun setLocationLatitude (latitude : Double) {
        val locationAux = Location("")
        locationAux.longitude = currentLocationObject.value.longitude
        locationAux.latitude = latitude

        currentLocationObject.value = locationAux
    }

    fun setLocationLongitude (longitude : Double) {
        val locationAux = Location("")
        locationAux.latitude = currentLocationObject.value.latitude
        locationAux.longitude = longitude

        currentLocationObject.value = locationAux
    }

    override fun onLocationUpdated(location: Location?) {
        currentLocationObject.value = location?: Location("")
        requestLocation.location().stop()
    }
}