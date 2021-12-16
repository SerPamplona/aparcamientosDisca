package com.sergiop.aparcamientos.ui.activitys.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiop.aparcamientos.model.Garaje
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.model.Persona
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository : DataRepository): ViewModel() {

    fun updatePropietarioParking (garaje : Garaje, persona : Persona) {

        viewModelScope.launch {
            val valueResult = async {repository.updatePropietario(garaje, persona) }
        }
    }
}