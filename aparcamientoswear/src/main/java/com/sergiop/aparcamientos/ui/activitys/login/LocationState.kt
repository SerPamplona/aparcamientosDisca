package com.sergiop.aparcamientos.ui.activitys.login

import android.location.Location

data class LocationState (
    val isLoading: Boolean = true,
    val error : Boolean = false,
    val messageError : String = "",
    val location : Location? = null,
    val plazaNameNear : String? = null
)