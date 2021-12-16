package com.sergiop.aparcamientos.ui.activitys.home.screens.listParking

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class ListParkingViewModel @Inject constructor(private val repository : DataRepository,
                                               private val userLogged : Persona): ViewModel() {

    private val _state = mutableStateOf(ListParkingState(loadingList = true))
    val listParkingState: State<ListParkingState> = _state

    var observerRemoveParkings = MutableStateFlow<Result<Boolean>>(Result.failure(Throwable()))

    init {
        requestListParking()
    }

    fun requestListParking() {
        viewModelScope.launch {
            _state.value = ListParkingState(loadingList = true)
            val valueResult = async { repository.allParking(userLogged) }
            val resultListParkings = valueResult.await()
            if (resultListParkings.isSuccess) {
                _state.value = ListParkingState(loadingList = false, list = resultListParkings.getOrNull())
            } else {
                _state.value = ListParkingState(loadingList = false, list = null, messageError = resultListParkings.exceptionOrNull()?.message?: "", error = true)
            }
        }
    }

    fun deleteParking(garaje : Garaje) {
        viewModelScope.launch {
            val valueResult = async { repository.deleteParking(garaje = garaje) }
            observerRemoveParkings.value = valueResult.await()
            requestListParking()
        }
    }

}