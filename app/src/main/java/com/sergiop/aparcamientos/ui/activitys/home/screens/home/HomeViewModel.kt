package com.sergiop.aparcamientos.ui.activitys.home.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.notifications.AppNotificacionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository : DataRepository,
                                        private val notificationManagerLocal : AppNotificacionManager): ViewModel() {

    private val _state = mutableStateOf(HomeState(loadingParking = true, error = false))
    val parkingState: State<HomeState> = _state

}