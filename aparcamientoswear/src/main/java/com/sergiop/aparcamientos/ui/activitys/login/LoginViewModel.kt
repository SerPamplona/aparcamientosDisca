package com.sergiop.aparcamientos.ui.activitys.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.sergiop.aparcamientos.AparcamientoWearApplication
import com.sergiop.aparcamientos.NEAR_GARAGE_KEY
import com.sergiop.aparcamientos.USER_KEY
import com.sergiop.aparcamientos.data.DataRepository
import com.sergiop.aparcamientos.data.LOGIN_OPERATION_RESPONSE
import com.sergiop.aparcamientos.data.NEAR_GARAGE_OPERATION_RESPONSE
import com.sergiop.aparcamientos.model.Persona
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(   private val repository : DataRepository,
                                            private val wearClient : DataClient): ViewModel(), DataClient.OnDataChangedListener {

    @Inject
    lateinit var packageManager : PackageManager

    private val _stateLogin = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _stateLogin

    private val _stateLocation = mutableStateOf(LocationState())
    val locationState: State<LocationState> = _stateLocation

    init {
        login()
        wearClient.addListener(this)
    }

    fun login () {

        viewModelScope.launch {
            _stateLogin.value = LoginState(isLoading = true)
            val valueResult = viewModelScope.async {repository.login()}
            valueResult.await()
        }

    }

    fun startLocation (context : Context) {

        if (hasGps()) {
            _stateLocation.value = LocationState(isLoading = true, error = false)
            SmartLocation.with(context).location()
                .oneFix()
                .start { location ->
                    location?.let {

                        _stateLocation.value = LocationState(
                            isLoading = false,
                            error = false,
                            location = it
                        )

                        viewModelScope.launch {
                            val valueResult = async { repository.nearGarage(it) }
                            valueResult.await()
                        }
                    }
                }

        } else {
            _stateLocation.value = LocationState(isLoading = false, messageError = "El dispositvo no tiene GPS")
        }
    }

    override fun onCleared() {
        super.onCleared()
        wearClient.removeListener(this)
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            // DataItem changed
            if (event.type == DataEvent.TYPE_CHANGED) {
                event.dataItem.also { item ->
                    if (item.uri.path?.compareTo(LOGIN_OPERATION_RESPONSE) == 0) {
                        DataMapItem.fromDataItem(item).dataMap.apply {
                            val userLogin = this.get<String>(USER_KEY)
                            _stateLogin.value = LoginState(  isLoading = false,
                                                        requestSend = true,
                                                        userLogged = Persona(username = userLogin))
                        }
                    } else if (item.uri.path?.compareTo(NEAR_GARAGE_OPERATION_RESPONSE) == 0) {
                        DataMapItem.fromDataItem(item).dataMap.apply {
                            val nearGarage = this.get<String>(NEAR_GARAGE_KEY)

                            _stateLocation.value = LocationState(
                                isLoading = false,
                                error = false,
                                plazaNameNear = nearGarage
                            )
                        }
                    }
                }
            } else if (event.type == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    private fun hasGps(): Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)

}