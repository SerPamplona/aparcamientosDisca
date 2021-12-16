package com.sergiop.aparcamientos.ui.activitys.home.screens.listParking

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.ui.activitys.home.screens.allListAllParking.AllListParkingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class AllListParkingViewModel @Inject constructor(private val repository : DataRepository): ViewModel() {

    private val _state = mutableStateOf(AllListParkingState(loadingList = true))
    val listParkingState: State<AllListParkingState> = _state

    init {
        requestListParking()
    }

    fun requestListParking() {
        viewModelScope.launch {
            _state.value = AllListParkingState(loadingList = true)
            delay(5000)
            val valueResult = async { repository.allParking(null) }
            val resultAllListParkings = valueResult.await()
            if (resultAllListParkings.isSuccess) {
                _state.value = AllListParkingState(loadingList = false, list = resultAllListParkings.getOrNull())
            } else {
                _state.value = AllListParkingState(loadingList = false, list = null, messageError = resultAllListParkings.exceptionOrNull()?.message?: "", error = true)
            }
        }
    }

}