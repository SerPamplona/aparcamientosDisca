package com.sergiop.aparcamientos.ui.activitys.home.screens.registerParking

import com.sergiop.aparcamientos.model.Garaje

data class RegisterParkingState  (
    val isRegister: Boolean = false,
    val ParkingRegister: Garaje? = null,
    val error: Boolean = false,
    val messageError : String = ""
)