package com.sergiop.aparcamientos.ui.activitys.home.screens.help

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.model.Persona
import com.sergiop.aparcamientos.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(private val repository : DataRepository): ViewModel() {

    //se crea una lista observable
    var observerRegisterGarage = MutableStateFlow<Result<String?>>(Result.failure(Throwable()))

    var observerListParkingsLoading = MutableStateFlow<Boolean>(false)
    var listParkings : Result<List<Garaje>>? = null

    var observerRemoveParkings = MutableStateFlow<Result<Boolean>>(Result.failure(Throwable()))

    //se crean las variable, para apartir de ellas, darle valor desde la vista
    var largo : Float = 0.0f
    var ancho : Float = 0.0f
    var moto : Boolean = false
    var direccion : String  = ""

    //se crea el observable de la ubicacion
    var currentLocationObject = MutableStateFlow(Location(""))

    fun registerParking () {

        //se crea unagaraje con los valores a guardar
        val garage = Garaje(moto, largo, ancho, currentLocationObject.value.latitude, currentLocationObject.value.longitude, null, direccion = direccion)

        viewModelScope.launch {
            val valueResult = async {repository.registerParking(garage) }
            observerRegisterGarage.value = valueResult.await()
        }
    }

}