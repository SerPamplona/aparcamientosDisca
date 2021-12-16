package com.sergiop.aparcamientos.ui.activitys.home.screens.home

import com.sergiop.aparcamientos.model.Garaje

data class HomeState (
    val loadingParking: Boolean = false,
    val nearGarage: Garaje? = null,
    val error: Boolean = false,
    val messageError : String = ""
)